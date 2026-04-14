package com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.steps

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AccountStep(
    onNext: (String, String) -> Unit,
    onBack: () -> Unit
){

    var nickname by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isFormValid = nickname.trim().isNotEmpty() && password.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ){

        Text(
            text = "Crea tu cuenta",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = nickname,
            onValueChange = { nickname = it },
            label = { Text("Nickname") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){

            Button(
                onClick = onBack
            ){
                Text("Volver")
            }

            Button(
                onClick = {
                    onNext(nickname.trim(), password)
                },
                enabled = isFormValid
            ){
                Text("Continuar")
            }
        }
    }
}
