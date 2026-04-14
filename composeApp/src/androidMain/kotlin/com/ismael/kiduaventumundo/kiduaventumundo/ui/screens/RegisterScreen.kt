package com.ismael.kiduaventumundo.kiduaventumundo.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.ismael.kiduaventumundo.kiduaventumundo.R
import com.ismael.kiduaventumundo.kiduaventumundo.ui.components.*
import com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.steps.*
import com.ismael.kiduaventumundo.kiduaventumundo.ui.model.Avatar
import com.ismael.kiduaventumundo.kiduaventumundo.ui.viewmodel.RegisterViewModel

// Importamos el AppContainerProvider
// Este objeto nos da acceso a todas las dependencias de la aplicación
import com.ismael.kiduaventumundo.kiduaventumundo.di.AppContainerProvider

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit
) {

    /*
        Aquí es donde utilizamos el AppContainer.

        En lugar de crear manualmente todas las dependencias:

            UserRepositoryImpl(
                UserApi(ApiClient())
            )

        simplemente pedimos el repository al contenedor de la aplicación.

        Esto tiene varias ventajas:

        1) Evita repetir código en todas las pantallas
        2) Centraliza las dependencias
        3) Facilita mantenimiento
        4) Permite cambiar implementaciones fácilmente

        El AppContainer ya creó el UserRepository por nosotros.
    */

    val viewModel = remember {
        RegisterViewModel(
            AppContainerProvider.container.userRepository,
            AppContainerProvider.container.sessionRepository
        )
    }

    // Controla el paso actual del registro
    var step by rememberSaveable { mutableStateOf(1) }

    // Estado del ViewModel
    val uiState = viewModel.uiState

    /*
        Lista de avatares disponibles

    */
    val avatars = listOf(
        Avatar(1, R.drawable.avatar_gato),
        Avatar(2, R.drawable.avatar_perro),
        Avatar(3, R.drawable.avatar_leon),
        Avatar(4, R.drawable.avatar_oso)
    )

    Box(modifier = Modifier.fillMaxSize()) {

        // Fondo del registro
        Image(
            painter = painterResource(id = R.drawable.fondo_registro),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        CloudLayer()
        FlowerLayer()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(60.dp))

            // Barra de progreso del registro
            ProgressBar(step)

            Spacer(modifier = Modifier.weight(1f))

            RegisterCard {
                if (uiState.success) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Usuario creado con exito",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color(0xFF1E3A5F)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Tu cuenta ya esta lista. Ahora puedes iniciar sesion.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF57718F)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = onRegisterSuccess,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4A7CDB),
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Ir a iniciar sesion",
                                fontSize = 16.sp
                            )
                        }
                    }
                } else {
                    /*
                        AnimatedContent permite animar el cambio
                        entre los pasos del registro.
                    */
                    AnimatedContent(
                        targetState = step,
                        transitionSpec = {

                            if (targetState > initialState) {

                                slideInHorizontally(
                                    animationSpec = tween(350),
                                    initialOffsetX = { it }
                                ) + fadeIn() togetherWith

                                        slideOutHorizontally(
                                            animationSpec = tween(350),
                                            targetOffsetX = { -it }
                                        ) + fadeOut()

                            } else {

                                slideInHorizontally(
                                    animationSpec = tween(350),
                                    initialOffsetX = { -it }
                                ) + fadeIn() togetherWith

                                        slideOutHorizontally(
                                            animationSpec = tween(350),
                                            targetOffsetX = { it }
                                        ) + fadeOut()
                            }

                        },
                        label = ""
                    ) { currentStep ->

                        when (currentStep) {

                            // Paso 1: nombre
                            1 -> NameStep(
                                onNext = { name ->
                                    viewModel.updateName(name)
                                    step = 2
                                }
                            )

                            // Paso 2: edad
                            2 -> AgeStep(
                                onNext = { age ->
                                    viewModel.updateAge(age)
                                    step = 3
                                },
                                onBack = { step = 1 }
                            )

                            // Paso 3: selección de avatar
                            3 -> AvatarStep(
                                avatars = avatars,
                                onNext = { avatarId ->
                                    viewModel.updateAvatar("avatar_$avatarId")
                                    step = 4
                                },
                                onBack = { step = 2 }
                            )

                            // Paso 4: cuenta
                            4 -> AccountStep(
                                onNext = { nickname, password ->
                                    viewModel.updateNickname(nickname)
                                    viewModel.updatePassword(password)
                                    step = 5
                                },
                                onBack = { step = 3 }
                            )

                            // Paso 5: seguridad
                            5 -> SecurityStep(
                                onFinish = { question, answer ->
                                    viewModel.updateSecurityQuestion(question)
                                    viewModel.updateSecurityAnswer(answer)

                                    // Aquí se ejecuta el registro
                                    viewModel.registerUser()
                                },
                                onBack = { step = 4 }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
