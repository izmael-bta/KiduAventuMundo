package com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.steps

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun AgeStep(
    onNext: (Int) -> Unit,
    onBack: () -> Unit
){

    var age by remember { mutableStateOf("") }
    val parsedAge = age.toIntOrNull()
    val showAgeError = age.isNotBlank() && (parsedAge == null || parsedAge <= 0)
    val isAgeValid = parsedAge != null && parsedAge > 0

    Column {

        Text(
            text = "¿Cuántos años tienes?",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = age,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    age = newValue
                }
            },
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = showAgeError,
            supportingText = {
                if (showAgeError) {
                    Text("Ingresa una edad mayor a 0")
                }
            }
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
                onNext(parsedAge ?: -1)
            },
                enabled = isAgeValid
            ){
                Text("Continuar")
            }

        }

    }
}
