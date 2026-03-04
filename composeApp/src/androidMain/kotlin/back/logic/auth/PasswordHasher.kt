package com.ismael.kiduaventumundo.kiduaventumundo.back.logic.auth

import java.security.MessageDigest

/**
 * Utilidad para hash de contrasenas y respuestas de seguridad.
 *
 * Nota: se usa SHA-256 para compatibilidad simple con el modelo actual.
 */
object PasswordHasher {
    /**
     * Devuelve hash SHA-256 en formato hexadecimal.
     */
    fun hash(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256")
            .digest(password.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
