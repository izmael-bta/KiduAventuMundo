package com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.steps

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AgeStep(
    onNext: (Int) -> Unit,
    onBack: () -> Unit
){

    var age by remember { mutableStateOf("") }

    Column {

        Text(
            text = "¿Cuántos años tienes?",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Edad") },
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
                onNext(age.toIntOrNull() ?: 0)
            }){
                Text("Continuar")
            }

        }

    }
}