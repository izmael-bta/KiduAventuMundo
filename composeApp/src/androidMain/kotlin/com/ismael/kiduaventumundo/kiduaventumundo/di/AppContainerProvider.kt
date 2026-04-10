package com.ismael.kiduaventumundo.kiduaventumundo.di

/*
    AppContainerProvider

    Este objeto expone una única instancia del AppContainer para toda la aplicación.

    Esto permite acceder al contenedor desde cualquier pantalla sin tener que
    crear múltiples instancias.

    Funciona como un "Singleton" simple.

    Ejemplo de uso en un Screen:

        val viewModel = remember {
            RegisterViewModel(
                AppContainerProvider.container.userRepository
            )
        }
*/

object AppContainerProvider {

    val container = AppContainer()

}