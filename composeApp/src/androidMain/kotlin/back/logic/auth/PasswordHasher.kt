package com.ismael.kiduaventumundo.kiduaventumundo.back.logic.auth

import java.security.MessageDigest

object PasswordHasher {
    fun hash(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256")
            .digest(password.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
