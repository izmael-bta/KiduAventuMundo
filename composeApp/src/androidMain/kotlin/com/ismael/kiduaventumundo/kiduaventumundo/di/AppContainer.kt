package com.ismael.kiduaventumundo.kiduaventumundo.di
/*
    AppContainer

    Este archivo funciona como un contenedor de dependencias de la aplicación.

    ¿Por qué existe?

    En una arquitectura limpia (Clean Architecture), los ViewModels no deberían crear
    directamente sus dependencias (Repositories, APIs, Clients, etc). Si lo hicieran,
    cada pantalla tendría código repetido como:

        UserRepositoryImpl(
            UserApi(ApiClient())
        )

    Esto genera varios problemas:

    - Código duplicado en muchas pantallas
    - Difícil mantenimiento
    - Difícil cambiar implementaciones
    - Dependencias desordenadas

    El AppContainer centraliza la creación de objetos importantes de la app,
    permitiendo reutilizarlos en cualquier pantalla.

    ¿Dónde se usa?

    Normalmente se utiliza cuando se crea un ViewModel dentro de un Screen.

    Ejemplo de uso:

        val viewModel = remember {
            RegisterViewModel(
                AppContainerProvider.container.userRepository
            )
        }

    De esta forma:

        Screen
          ↓
        ViewModel
          ↓
        Repository
          ↓
        API
          ↓
        Backend

    Esto es una forma simple de Dependency Injection sin usar frameworks
    como Hilt o Koin.
*/

import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.ApiClient
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.api.UserApi
import com.ismael.kiduaventumundo.kiduaventumundo.datasource.repository.UserRepositoryImpl

class AppContainer {

    /*
        Cliente HTTP global de la aplicación.
        Maneja la conexión con el backend.
    */
    private val apiClient = ApiClient()

    /*
        API encargada de comunicarse con los endpoints de usuario.
    */
    private val userApi = UserApi(apiClient)

    /*
        Repository que conecta la capa de dominio con la capa de datos.
    */
    val userRepository = UserRepositoryImpl(userApi)

}