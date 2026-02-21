package com.ismael.kiduaventumundo.kiduaventumundo.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import com.ismael.kiduaventumundo.kiduaventumundo.domain.operations.RegistrarUsuario
import front.models.UserProfileUi
import com.ismael.kiduaventumundo.ui.viewmodel.RegisterResult

class RegisterViewModel(
    private val registrarUsuario: RegistrarUsuario
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun register(profile: UserProfileUi) {

        _uiState.value = RegisterUiState(isLoading = true)

        val result = registrarUsuario(profile)

        _uiState.value = when (result) {
            is RegisterResult.Success -> RegisterUiState(isSuccess = true)
            is RegisterResult.Error -> RegisterUiState(error = result.message)
        }
    }
}