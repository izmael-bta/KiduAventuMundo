package com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.steps

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SecurityStep(
    onFinish: (String, String) -> Unit,
    onBack: () -> Unit
){

    var question by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }

    Column {

        Text(
            text = "Pregunta de seguridad",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = question,
            onValueChange = { question = it },
            label = { Text("Pregunta") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = answer,
            onValueChange = { answer = it },
            label = { Text("Respuesta") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){

            Button(onClick = onBack){
                Text("Volver")
            }

            Button(onClick = {
                onFinish(question, answer)
            }){
                Text("Crear Cuenta")
            }

        }

    }
}