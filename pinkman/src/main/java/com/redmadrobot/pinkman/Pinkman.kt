package com.redmadrobot.pinkman

import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties.*
import android.util.Log
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import com.lambdapioneer.argon2kt.Argon2Exception
import com.redmadrobot.pinkman.exception.BlacklistedPinException
import com.redmadrobot.pinkman.internal.Salt
import com.redmadrobot.pinkman.internal.argon2.Argon2
import com.redmadrobot.pinkman.internal.exception.BadHashException
import com.redmadrobot.pinkman.internal.pbkdf2.Pbkdf2Factory
import com.redmadrobot.pinkman.internal.pbkdf2.Pbkdf2Key
import java.io.File
import java.io.ObjectInputStream
import java.security.GeneralSecurityException

class Pinkman(
    private val applicationContext: Context,
    private val storageName: String = "pinkman",
    private val pinBlacklist: List<String>? = null
) {
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
                    val isDeviceSecure = isDeviceSecure()

                    setUnlockedDeviceRequired(isDeviceSecure)

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

    private fun isDeviceSecure(): Boolean {
        val keyguardManager = applicationContext
            .getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        return keyguardManager.isDeviceSecure
    }

    @Throws(BlacklistedPinException::class)
    fun createPin(newPin: String, force: Boolean = false) {
        checkBlacklisted(newPin)

        if (force) {
            storageFile.delete()
        }

        val hash = Argon2.createHash(newPin.toByteArray(), Salt.generate())

        encryptedStorage.openFileOutput().use {
            it.write(hash)
        }
    }

    fun removePin(): Boolean {
        return storageFile.delete()
    }

    fun changePin(oldPin: String, newPin: String) {
        require(storageFile.exists()) { "PIN is not set. Please create PIN before changing." }
        checkBlacklisted(newPin)

        return if (isValidPin(oldPin)) {
            createPin(newPin, force = true)
        } else {
            throw GeneralSecurityException("Old PIN is not valid.")
        }
    }

    fun isValidPin(inputPin: String): Boolean {
        require(storageFile.exists()) { "PIN is not set. Please create PIN before validating." }

        return try {
            val storedHash = loadHashFromStorage()
            Argon2.verifyHash(storedHash, inputPin.toByteArray())
        } catch (e: BadHashException) {
            Log.d(TAG, e.localizedMessage, e)
            fallbackValidationWithMigration(inputPin)
        } catch (e: Argon2Exception) {
            Log.e(TAG, e.localizedMessage, e)
            false
        }
    }

    fun isPinSet(): Boolean {
        return if (storageFile.exists()) {
            val hash = loadHashFromStorage()

            hash.isNotEmpty()
        } else {
            false
        }
    }

    private fun loadHashFromStorage() = encryptedStorage.openFileInput().use { it.readBytes() }

    // TODO: Need to delete this method in the next major release
    private fun fallbackValidationWithMigration(inputPin: String): Boolean {
        val pbkdf2Key = ObjectInputStream(encryptedStorage.openFileInput()).use {
            it.readObject() as Pbkdf2Key
        }

        val inputKey = Pbkdf2Factory.createKey(
            inputPin.toCharArray(),
            pbkdf2Key.salt,
            pbkdf2Key.algorithm,
            pbkdf2Key.iterations
        )

        return if (pbkdf2Key.hash contentEquals inputKey.hash) {
            createPin(inputPin, true)

            true
        } else {
            false
        }
    }

    private inline fun checkBlacklisted(pin: String) {
        if (pinBlacklist != null && pinBlacklist.contains(pin)) {
            throw BlacklistedPinException()
        }
    }

    data class Config(val useStrongBoxIfPossible: Boolean = true)

    companion object {
        private const val TAG = "PINkman"

        val DEFAULT_BLACKLIST = listOf(
            "1234", // Freq: 10.713%
            "1111", // Freq: 6.016%
            "0000", // Freq: 1.881%
            "1212" // Freq: 1.197%
        )

        private const val KEYSET_ALIAS = "pinkman_keyset"
        private const val PREFERENCE_FILE = "pinkman_preferences"
        private const val KEYSTORE_ALIAS = "pinkman_key"
        private const val KEY_SIZE = 256
    }
}
