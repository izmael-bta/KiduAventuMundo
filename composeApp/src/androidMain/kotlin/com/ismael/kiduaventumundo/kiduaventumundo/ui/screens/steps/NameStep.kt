package com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.steps

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NameStep(
    onNext: (String) -> Unit
){

    var name by remember { mutableStateOf("") }

    Column {

        Text(
            text = "¿Cómo te llamas?",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { onNext(name) },
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Continuar")
        }

    }
}