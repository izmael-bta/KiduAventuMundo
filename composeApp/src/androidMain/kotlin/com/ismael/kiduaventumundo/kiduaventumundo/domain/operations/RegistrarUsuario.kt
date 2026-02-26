package com.ismael.kiduaventumundo.kiduaventumundo.domain.operations

import com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.domain.model.User
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.auth.PasswordHasher
import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.userRepository
import front.models.UserProfileUi
import com.ismael.kiduaventumundo.ui.viewmodel.RegisterResult

class RegistrarUsuario(
    private val repository: userRepository
) {

    operator fun invoke(profile: UserProfileUi): RegisterResult {
        if (profile.password.length < 6) {
            return RegisterResult.Error("La contrasena debe tener al menos 6 caracteres.")
        }

        if (repository.nicknameExists(profile.username)) {
            return RegisterResult.Error("El nickname ya existe.")
        }

        val user = User(
            name = profile.name,
            age = profile.age,
            nickname = profile.username,
            passwordHash = PasswordHasher.hash(profile.password),
            avatarId = "avatar_${profile.avatarId}",
            stars = 0
        )

        val id = repository.register(user)

        return if (id != -1L) {
            repository.setSession(id)
            RegisterResult.Success
        } else {
            RegisterResult.Error("Error al crear usuario.")
        }
    }
}
