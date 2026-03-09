package com.ismael.kiduaventumundo.kiduaventumundo.domain.operations

import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.auth.PasswordHasher
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.User
import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.SessionRepository
import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.UserRepository
import com.ismael.kiduaventumundo.kiduaventumundo.domain.session.UserSession
import com.ismael.kiduaventumundo.ui.viewmodel.RegisterResult
import front.models.UserProfileUi

class RegistrarUsuario(
    private val repository: UserRepository,
    private val sessionRepository: SessionRepository
) {

    suspend operator fun invoke(profile: UserProfileUi): RegisterResult {
        if (profile.password.length < 6) {
            return RegisterResult.Error("La contrasena debe tener al menos 6 caracteres.")
        }

        if (profile.securityQuestion.isBlank()) {
            return RegisterResult.Error("Selecciona una pregunta de seguridad.")
        }

        if (profile.securityAnswer.trim().length < 2) {
            return RegisterResult.Error("La respuesta de seguridad debe tener al menos 2 caracteres.")
        }

        if (repository.nicknameExists(profile.username)) {
            return RegisterResult.Error("El nickname ya existe.")
        }

        val createdUser = repository.register(
            User(
                name = profile.name,
                age = profile.age,
                nickname = profile.username,
                passwordHash = PasswordHasher.hash(profile.password),
                avatarId = "avatar_${profile.avatarId}",
                securityQuestion = profile.securityQuestion.trim(),
                securityAnswerHash = PasswordHasher.hash(profile.securityAnswer.trim().lowercase()),
                stars = 0
            )
        )

        return if (createdUser != null) {
            UserSession.setUser(createdUser)
            sessionRepository.setSessionUserId(createdUser.id)
            RegisterResult.Success
        } else {
            RegisterResult.Error(repository.getLastErrorMessage() ?: "Error al crear usuario.")
        }
    }
}
