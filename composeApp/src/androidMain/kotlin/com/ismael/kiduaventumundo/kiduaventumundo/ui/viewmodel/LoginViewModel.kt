package com.ismael.kiduaventumundo.kiduaventumundo.ui.viewmodel

import androidx.lifecycle.ViewModel
//import com.ismael.kiduaventumundo.domain.repository.UserRepository
import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.userRepository



class LoginViewModel(
    private val repository: userRepository
) : ViewModel() {

    fun login(nickname: String): LoginResult {

        val nick = nickname.trim()

        if (nick.isBlank()) {
            return LoginResult.Error("Ingresa tu nickname.")
        }

        val user = repository.getUserByNickname(nick)

        if (user == null) {
            return LoginResult.Error("Usuario no encontrado. Crea una cuenta.")
        }

        repository.setSession(user.id)
        return LoginResult.Success
    }
}

sealed class LoginResult {
    object Success : LoginResult()
    data class Error(val message: String) : LoginResult()
}