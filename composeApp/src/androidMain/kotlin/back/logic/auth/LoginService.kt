package com.ismael.kiduaventumundo.kiduaventumundo.back.logic.auth

import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.SessionRepository
import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.UserRepository
import com.ismael.kiduaventumundo.kiduaventumundo.domain.session.UserSession

sealed class LoginResult {
    data object Success : LoginResult()
    data class Error(val message: String) : LoginResult()
}

class LoginService(
    private val repository: UserRepository,
    private val sessionRepository: SessionRepository
) {
    suspend fun login(nickname: String, password: String): LoginResult {
        val nick = nickname.trim()
        val pass = password

        if (nick.isBlank()) {
            return LoginResult.Error("Ingresa tu nickname.")
        }

        if (pass.isBlank()) {
            return LoginResult.Error("Ingresa tu contrasena.")
        }

        val response = repository.login(
            nickname = nick,
            passwordHash = PasswordHasher.hash(pass)
        )

        val user = response?.user
        return when {
            response == null -> LoginResult.Error(
                repository.getLastErrorMessage() ?: "No se pudo conectar al servidor."
            )
            response.success && user != null -> {
                UserSession.setUser(user)
                sessionRepository.setSessionUserId(user.id)
                LoginResult.Success
            }
            else -> LoginResult.Error(response.message ?: "Credenciales invalidas.")
        }
    }
}
