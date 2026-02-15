package com.ismael.kiduaventumundo.ui.viewmodel

sealed class RegisterResult {
    object Success : RegisterResult()
    data class Error(val message: String) : RegisterResult()
}