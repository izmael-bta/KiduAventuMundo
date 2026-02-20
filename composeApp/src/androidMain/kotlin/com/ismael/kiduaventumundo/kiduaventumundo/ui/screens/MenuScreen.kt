package com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.EnglishManager

@Composable
fun MenuScreen(
    nickname: String,
    onGoEnglish: () -> Unit,
    onGoProfile: () -> Unit
) {
    val stars = EnglishManager.stars.value

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Menu", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        Text("Hola, $nickname")
        Text("⭐ $stars")

        Spacer(Modifier.height(16.dp))
        Button(onClick = onGoEnglish, modifier = Modifier.fillMaxWidth()) {
            Text("Ingles")
        }

        Spacer(Modifier.height(10.dp))
        Button(onClick = onGoProfile, modifier = Modifier.fillMaxWidth()) {
            Text("Ajustes")
        }
    }
}
