package com.ismael.kiduaventumundo.kiduaventumundo.ui.viewmodel

data class RegisterUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)