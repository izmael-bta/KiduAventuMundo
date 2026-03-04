package com.ismael.kiduaventumundo.kiduaventumundo.back.logic.auth

import com.ismael.kiduaventumundo.kiduaventumundo.domain.operations.RegistrarUsuario
import com.ismael.kiduaventumundo.ui.viewmodel.RegisterResult
import front.models.UserProfileUi

/**
 * Resultado de envio del formulario de registro.
 */
sealed class RegisterSubmitResult {
    data object Success : RegisterSubmitResult()
    data class Error(val message: String) : RegisterSubmitResult()
}

/**
 * Adaptador entre UI de registro y caso de uso de dominio.
 */
object RegisterSubmitter {
    /**
     * Ejecuta registro y normaliza respuesta para capa UI.
     */
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
