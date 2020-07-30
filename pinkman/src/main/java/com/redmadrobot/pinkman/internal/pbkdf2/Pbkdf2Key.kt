package com.redmadrobot.pinkman.internal.pbkdf2

import java.io.Serializable

@Deprecated("Use Argon2 instead")
internal data class Pbkdf2Key(
    val algorithm: String,
    val iterations: Int,
    val salt: ByteArray,
    val hash: ByteArray
) : Serializable {
    companion object {
        private const val serialVersionUID = -90000099L
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Pbkdf2Key

        if (algorithm != other.algorithm) return false
        if (iterations != other.iterations) return false
        if (!salt.contentEquals(other.salt)) return false
        if (!hash.contentEquals(other.hash)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = algorithm.hashCode()
        result = 31 * result + iterations
        result = 31 * result + salt.contentHashCode()
        result = 31 * result + hash.contentHashCode()
        return result
    }
}
