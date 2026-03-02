package com.ismael.kiduaventumundo.kiduaventumundo.back.logic.auth

import com.ismael.kiduaventumundo.kiduaventumundo.domain.operations.RegistrarUsuario
import com.ismael.kiduaventumundo.ui.viewmodel.RegisterResult
import front.models.UserProfileUi

sealed class RegisterSubmitResult {
    data object Success : RegisterSubmitResult()
    data class Error(val message: String) : RegisterSubmitResult()
}

object RegisterSubmitter {
    fun submit(
        registrarUsuario: RegistrarUsuario,
        profile: UserProfileUi
    ): RegisterSubmitResult {
        return when (val result = registrarUsuario(profile)) {
            is RegisterResult.Success -> RegisterSubmitResult.Success
            is RegisterResult.Error -> RegisterSubmitResult.Error(result.message)
        }
    }
}
