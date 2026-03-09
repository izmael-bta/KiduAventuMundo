package com.ismael.kiduaventumundo.kiduaventumundo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.UserRepository
import com.ismael.kiduaventumundo.kiduaventumundo.domain.session.UserSession
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UserRepository
) : ViewModel() {

    fun login(nickname: String, onResult: (LoginResult) -> Unit) {
        val nick = nickname.trim()

        if (nick.isBlank()) {
            onResult(LoginResult.Error("Ingresa tu nickname."))
            return
        }

        viewModelScope.launch {
            val user = repository.getUserByNickname(nick)
            if (user == null) {
                onResult(LoginResult.Error("Usuario no encontrado. Crea una cuenta."))
            } else {
                UserSession.setUser(user)
                onResult(LoginResult.Success)
            }
        }
    }
}

sealed class LoginResult {
    data object Success : LoginResult()
    data class Error(val message: String) : LoginResult()
}
