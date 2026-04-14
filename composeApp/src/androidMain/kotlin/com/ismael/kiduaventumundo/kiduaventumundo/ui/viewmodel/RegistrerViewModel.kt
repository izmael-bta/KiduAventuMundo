package com.ismael.kiduaventumundo.kiduaventumundo.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/*
    ViewModel es parte de la arquitectura MVVM.
    Sirve para mantener el estado de la UI separado
    de la lógica de negocio.
*/
import androidx.lifecycle.ViewModel

/*
    viewModelScope permite ejecutar corrutinas
    ligadas al ciclo de vida del ViewModel.
*/
import androidx.lifecycle.viewModelScope


import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.auth.PasswordHasher
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.User

/*
    Repository que encapsula el acceso a la base de datos o API.
*/
import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.SessionRepository
import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.UserRepository
import com.ismael.kiduaventumundo.kiduaventumundo.domain.session.UserSession

import kotlinx.coroutines.launch


/*
    RegisterViewModel

    Este ViewModel controla toda la lógica de registro de usuario.

    Responsabilidades:

    - Mantener el estado de la pantalla de registro
    - Validar datos
    - Crear el usuario
    - Comunicarse con el repository
*/

class RegisterViewModel(
    private val repository: UserRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {


    /*
        Estado de la UI.

        mutableStateOf hace que Compose observe los cambios
        y actualice la interfaz automáticamente.
    */
    var uiState by mutableStateOf(RegisterUiState())
        private set


    /*
        Función principal que ejecuta el registro de usuario.
    */
    fun registerUser() {

        /*
            viewModelScope.launch ejecuta código asíncrono
            sin bloquear el hilo principal (UI).
        */
        viewModelScope.launch {


            /*
                Validación básica de campos obligatorios.
            */
            if (
                uiState.name.isBlank() ||
                uiState.age <= 0 ||
                uiState.nickname.isBlank() ||
                uiState.password.isBlank() ||
                uiState.avatarId.isBlank() ||
                uiState.securityQuestion.isBlank() ||
                uiState.securityAnswer.isBlank()
            ) {

                uiState = uiState.copy(
                    error = "Completa todos los campos y usa una edad valida"
                )

                return@launch
            }


            /*
                Indicamos a la UI que el proceso está cargando.
            */
            uiState = uiState.copy(
                isLoading = true,
                error = null
            )

            try {

                /*
                    Creamos el objeto User con la información
                    capturada en la pantalla de registro.
                */
                val user = User(
                    name = uiState.name,
                    age = uiState.age,
                    nickname = uiState.nickname,
                    passwordHash = PasswordHasher.hash(uiState.password),
                    avatarId = uiState.avatarId,
                    securityQuestion = uiState.securityQuestion,
                    securityAnswerHash = PasswordHasher.hash(
                        uiState.securityAnswer.trim().lowercase()
                    )
                )


                /*
                    Llamamos al repository para guardar el usuario.
                */
                val result = repository.register(user)


                /*
                    Si el repository devuelve un usuario válido,
                    significa que el registro fue exitoso.
                */
                if (result != null) {
                    UserSession.setUser(result)
                    sessionRepository.setSessionUserId(result.id)

                    uiState = uiState.copy(
                        isLoading = false,
                        success = true,
                        error = null
                    )

                } else {

                    /*
                        Si el registro falla mostramos el error
                        devuelto por el repository.
                    */
                    uiState = uiState.copy(
                        isLoading = false,
                        error = repository.getLastErrorMessage()
                            ?: "Error registrando usuario"
                    )

                }

            } catch (e: Exception) {

                /*
                    Captura errores inesperados
                    (problemas de red, base de datos, etc).
                */
                uiState = uiState.copy(
                    isLoading = false,
                    error = "Error registrando usuario"
                )

            }

        }

    }


    /*
        Las siguientes funciones actualizan partes específicas
        del estado de la UI.

        Usamos copy() porque RegisterUiState es un data class.
        Esto mantiene el estado inmutable.
    */


    fun updateName(name: String) {
        uiState = uiState.copy(name = name)
    }


    fun updateAge(age: Int) {
        uiState = uiState.copy(age = age)
    }


    fun updateNickname(nickname: String) {
        uiState = uiState.copy(nickname = nickname)
    }


    fun updatePassword(password: String) {
        uiState = uiState.copy(password = password)
    }


    fun updateSecurityQuestion(question: String) {
        uiState = uiState.copy(securityQuestion = question)
    }


    fun updateSecurityAnswer(answer: String) {
        uiState = uiState.copy(securityAnswer = answer)
    }


    fun updateAvatar(avatarId: String) {
        uiState = uiState.copy(avatarId = avatarId)
    }

}
