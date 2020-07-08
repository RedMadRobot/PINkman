package com.redmadrobot.pinkman

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties.*
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import com.redmadrobot.pinkman.internal.Pbkdf2Factory
import com.redmadrobot.pinkman.internal.Pbkdf2Key
import com.redmadrobot.pinkman.internal.Salt
import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.security.GeneralSecurityException

class Pinkman(
    private val applicationContext: Context,
    private val storageName: String = "pinkman"
) {
    companion object {
        private const val KEYSET_ALIAS = "pinkman_keyset"
        private const val PREFERENCE_FILE = "pinkman_preferences"
        private const val KEYSTORE_ALIAS = "pinkman_key"
        private const val KEY_SIZE = 256
    }

    private val storageFile by lazy {
        File(applicationContext.filesDir, storageName)
    }

    private val keySpec =
        KeyGenParameterSpec.Builder(KEYSTORE_ALIAS, PURPOSE_ENCRYPT or PURPOSE_DECRYPT)
            .setBlockModes(BLOCK_MODE_GCM)
            .setEncryptionPaddings(ENCRYPTION_PADDING_NONE)
            .setKeySize(KEY_SIZE)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    setUnlockedDeviceRequired(true)

                    val hasStrongBox = applicationContext
                        .packageManager
                        .hasSystemFeature(PackageManager.FEATURE_STRONGBOX_KEYSTORE)

                    setIsStrongBoxBacked(hasStrongBox)
                }
            }.build()


    private val encryptedStorage by lazy {
        EncryptedFile.Builder(
            storageFile,
            applicationContext,
            MasterKeys.getOrCreate(keySpec),
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).setKeysetAlias(KEYSET_ALIAS).setKeysetPrefName(PREFERENCE_FILE).build()
    }

    fun createPin(newPin: String, force: Boolean = false) {
        if (force) {
            storageFile.delete()
        }

        val pbkdf2Key = Pbkdf2Factory.createKey(newPin.toCharArray(), Salt.generate())

        ObjectOutputStream(encryptedStorage.openFileOutput()).use {
            it.writeObject(pbkdf2Key)
        }
    }

    fun removePin(): Boolean {
        return storageFile.delete()
    }

    fun changePin(oldPin: String, newPin: String) {
        require(storageFile.exists()) { "PIN is not set. Please create PIN before changing." }

        return if (isValidPin(oldPin)) {
            createPin(newPin, force = true)
        } else {
            throw GeneralSecurityException("Old PIN is not valid.")
        }
    }

    fun isValidPin(inputPin: String): Boolean {
        require(storageFile.exists()) { "PIN is not set. Please create PIN before validating." }

        val storedKey = loadKeyFromStorage()

        val inputKey = Pbkdf2Factory.createKey(
            inputPin.toCharArray(),
            storedKey.salt,
            storedKey.algorithm,
            storedKey.iterations
        )

        return storedKey.hash contentEquals inputKey.hash
    }

    fun isPinSet(): Boolean {
        return if (storageFile.exists()) {
            val key = loadKeyFromStorage()

            key.hash.isNotEmpty()
        } else {
            false
        }
    }

    private fun loadKeyFromStorage(): Pbkdf2Key {
        require(storageFile.exists())

        return ObjectInputStream(encryptedStorage.openFileInput()).use {
            it.readObject() as Pbkdf2Key
        }
    }
}
