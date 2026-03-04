package com.ismael.kiduaventumundo.kiduaventumundo.back.logic.auth

import com.ismael.kiduaventumundo.kiduaventumundo.back.db.AppDatabaseHelper

/**
 * Resultado del intento de inicio de sesion.
 */
sealed class LoginResult {
    data object Success : LoginResult()
    data class Error(val message: String) : LoginResult()
}

/**
 * Caso de uso de autenticacion.
 *
 * Valida credenciales y registra sesion activa en DB local.
 */
class LoginService(
    private val db: AppDatabaseHelper
) {
    /**
     * Ejecuta login por nickname/password.
     */
    fun login(nickname: String, password: String): LoginResult {
        val nick = nickname.trim()
        val pass = password

        if (nick.isBlank()) {
            return LoginResult.Error("Ingresa tu nickname.")
        }

        if (pass.isBlank()) {
            return LoginResult.Error("Ingresa tu contraseña.")
        }

        val user = db.getUserByNickname(nick)
            ?: return LoginResult.Error("Usuario no encontrado.")

        val passwordHash = PasswordHasher.hash(pass)
        if (user.passwordHash != passwordHash) {
            return LoginResult.Error("Contraseña incorrecta.")
        }

        db.setSession(user.id)
        return LoginResult.Success
    }
}
