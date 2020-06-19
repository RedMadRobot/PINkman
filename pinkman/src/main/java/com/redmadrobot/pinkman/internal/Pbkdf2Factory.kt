package com.redmadrobot.pinkman.internal

import android.os.Build
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec


internal object Pbkdf2Factory {
    private const val DEFAULT_ITERATIONS = 10000

    private val systemAlgorithm by lazy {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            "PBKDF2withHmacSHA1"
        } else {
            "PBKDF2withHmacSHA256"
        }
    }

    fun createKey(
        passphraseOrPin: CharArray,
        salt: ByteArray,
        algorithm: String = systemAlgorithm,
        iterations: Int = DEFAULT_ITERATIONS
    ): Pbkdf2Key {
        val keySpec = PBEKeySpec(passphraseOrPin, salt, iterations, 256)
        val secretKey = SecretKeyFactory.getInstance(algorithm).generateSecret(keySpec)

        return Pbkdf2Key(
            secretKey.algorithm,
            iterations,
            salt,
            secretKey.encoded
        )
    }
}
