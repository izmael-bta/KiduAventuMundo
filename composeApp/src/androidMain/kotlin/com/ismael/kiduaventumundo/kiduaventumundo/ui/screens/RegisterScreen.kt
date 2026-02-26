package com.ismael.kiduaventumundo.kiduaventumundo.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.ismael.kiduaventumundo.kiduaventumundo.R
import com.ismael.kiduaventumundo.kiduaventumundo.domain.operations.RegistrarUsuario
import com.ismael.kiduaventumundo.kiduaventumundo.ui.components.CloudLayer
import com.ismael.kiduaventumundo.kiduaventumundo.ui.components.FlowerLayer
import com.ismael.kiduaventumundo.kiduaventumundo.ui.components.RegisterCard
import com.ismael.kiduaventumundo.ui.viewmodel.RegisterResult

@Composable
fun RegisterScreen(
    registrarUsuario: RegistrarUsuario,
    onRegisterSuccess: () -> Unit
) {
    var registerError by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo_registro),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0x22001838), Color(0x8A001838))
                    )
                )
        )

        CloudLayer()
        FlowerLayer()

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            RegisterCard(
                modifier = Modifier
                    .fillMaxWidth(0.87f)
                    .padding(top = 48.dp),
                errorMessage = registerError,
                onRegister = { profile ->
                    val result = registrarUsuario(profile)
                    if (result is RegisterResult.Success) {
                        registerError = null
                        onRegisterSuccess()
                    } else if (result is RegisterResult.Error) {
                        registerError = result.message
                    }
                }
            )
        }
    }
}
