package com.ismael.kiduaventumundo.kiduaventumundo.domain.operations

import com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.domain.model.User
import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.userRepository
import front.models.UserProfileUi
import com.ismael.kiduaventumundo.ui.viewmodel.RegisterResult

class RegistrarUsuario(
    private val repository: userRepository
) {

    operator fun invoke(profile: UserProfileUi): RegisterResult {

        if (repository.nicknameExists(profile.username)) {
            return RegisterResult.Error("El nickname ya existe.")
        }

        val user = User(
            name = profile.name,
            age = profile.age,
            nickname = profile.username,
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