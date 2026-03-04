package com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class EnglishLevel(
    val level: Int,
    val title: String,
    val description: String,
    val isUnlocked: Boolean,
    val isCompleted: Boolean
)

@Composable
fun EnglishMenuScreen(
    levels: List<EnglishLevel>,
    onBack: () -> Unit,
    onLevelClick: (Int) -> Unit
) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Inglés - Niveles", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        Text("Selecciona un nivel para jugar", style = MaterialTheme.typography.bodyMedium)

        Spacer(Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(levels) { lvl ->
                LevelCard(
                    lvl = lvl,
                    onClick = { onLevelClick(lvl.level) }
                )
            }
        }

        OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Volver")
        }
    }
}

@Composable
private fun LevelCard(
    lvl: EnglishLevel,
    onClick: () -> Unit
) {
    val statusText = when {
        lvl.isCompleted -> "? Completado"
        lvl.isUnlocked -> "?? Disponible"
        else -> "?? Bloqueado"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (lvl.isUnlocked) Modifier.clickable { onClick() } else Modifier
            )
    ) {
        Column(Modifier.padding(14.dp)) {
            Text("Nivel ${lvl.level}: ${lvl.title}", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(lvl.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            Text(statusText, style = MaterialTheme.typography.labelLarge)
        }
    }
}
