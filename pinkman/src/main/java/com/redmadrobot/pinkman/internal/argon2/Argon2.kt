package com.redmadrobot.pinkman.internal.argon2

import com.lambdapioneer.argon2kt.Argon2Kt
import com.lambdapioneer.argon2kt.Argon2Mode
import java.security.GeneralSecurityException


internal object Argon2 {
    private const val DEFAULT_ITERATIONS = 5
    private const val DEFAULT_MEMORY_KB = 65536

    private val DEFAULT_MODE = Argon2Mode.ARGON2_I

    private val hashModeRegex = Regex("""argon2(i?d?)""")

    private val argon2instance = Argon2Kt()

    fun createHash(
        passphraseOrPin: ByteArray,
        salt: ByteArray,
        mode: Argon2Mode = DEFAULT_MODE,
        iterations: Int = DEFAULT_ITERATIONS,
        memory: Int = DEFAULT_MEMORY_KB
    ): ByteArray {
        return argon2instance.hash(mode, passphraseOrPin, salt, iterations, memory)
            .encodedOutputAsByteArray()
    }

    fun verifyHash(encodedHash: ByteArray, passphraseOrPin: ByteArray): Boolean {
        val hashAsString = String(encodedHash)

        return argon2instance.verify(
            parseHashMode(hashAsString),
            hashAsString,
            passphraseOrPin
        )
    }

    private fun parseHashMode(encodedHash: String): Argon2Mode {
        val res = hashModeRegex.find(encodedHash)

        return when (res?.value) {
            "argon2i" -> Argon2Mode.ARGON2_I
            "argon2d" -> Argon2Mode.ARGON2_D
            "argon2id" -> Argon2Mode.ARGON2_ID
            else -> throw GeneralSecurityException("Hash is corrupted")
        }
    }
}
