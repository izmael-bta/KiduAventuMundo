package com.ismael.kiduaventumundo.kiduaventumundo.ui.viewmodel

data class RegisterUiState(

    val name: String = "",
    val age: Int = 0,
    val nickname: String = "",
    val password: String = "",
    val avatarId: String = "avatar_1",
    val securityQuestion: String = "",
    val securityAnswer: String = "",

    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)