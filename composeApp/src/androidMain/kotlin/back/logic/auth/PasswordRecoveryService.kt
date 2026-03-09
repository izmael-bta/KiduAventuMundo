package com.ismael.kiduaventumundo.kiduaventumundo.back.logic.auth

import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.UserRepository

sealed class SecurityQuestionResult {
    data class Success(val question: String) : SecurityQuestionResult()
    data class Error(val message: String) : SecurityQuestionResult()
}

sealed class PasswordResetResult {
    data object Success : PasswordResetResult()
    data class Error(val message: String) : PasswordResetResult()
}

class PasswordRecoveryService(
    private val repository: UserRepository
) {
    private var userIdForReset: Long? = null

    suspend fun getSecurityQuestion(nickname: String): SecurityQuestionResult {
        val nick = nickname.trim()
        if (nick.isBlank()) {
            return SecurityQuestionResult.Error("Ingresa tu nickname.")
        }

        val user = repository.getUserByNickname(nick)
            ?: return SecurityQuestionResult.Error("Usuario no encontrado.")

        userIdForReset = user.id
        return SecurityQuestionResult.Success(user.securityQuestion)
    }

    suspend fun resetPassword(
        nickname: String,
        securityAnswer: String,
        newPassword: String
    ): PasswordResetResult {
        val nick = nickname.trim()
        val answer = securityAnswer.trim()
        val password = newPassword

        if (nick.isBlank()) {
            return PasswordResetResult.Error("Ingresa tu nickname.")
        }
        if (answer.isBlank()) {
            return PasswordResetResult.Error("Ingresa la respuesta de seguridad.")
        }
        if (password.length < 6) {
            return PasswordResetResult.Error("La contrasena debe tener al menos 6 caracteres.")
        }

        val user = repository.getUserByNickname(nick)
            ?: return PasswordResetResult.Error("Usuario no encontrado.")

        val answerHash = PasswordHasher.hash(answer.lowercase())
        if (answerHash != user.securityAnswerHash) {
            return PasswordResetResult.Error("Respuesta de seguridad incorrecta.")
        }

        val updated = repository.updatePassword(
            userId = userIdForReset ?: user.id,
            passwordHash = PasswordHasher.hash(password)
        )

        return if (updated) {
            PasswordResetResult.Success
        } else {
            PasswordResetResult.Error("No se pudo actualizar la contrasena.")
        }
    }
}
