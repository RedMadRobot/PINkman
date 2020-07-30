package com.redmadrobot.pinkman.internal

import java.security.SecureRandom

internal object Salt {
    fun generate(length: Int = 32): ByteArray {
        val random = SecureRandom()
        val salt = ByteArray(length)

        random.nextBytes(salt)

        return salt
    }
}
