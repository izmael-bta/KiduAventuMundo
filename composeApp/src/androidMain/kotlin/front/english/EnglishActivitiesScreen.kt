package com.ismael.kiduaventumundo.kiduaventumundo.front.english

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.EnglishManager

@Composable
fun EnglishActivitiesScreen(
    level: Int,
    levelTitle: String,
    totalActivities: Int,
    onBack: () -> Unit,
    onStartActivity: (Int) -> Unit
) {
    val starsByActivity = EnglishManager.getActivityStars(level, totalActivities)
    val activities = (0 until totalActivities).toList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Nivel $level: $levelTitle", style = MaterialTheme.typography.headlineSmall)
        Text("Lista de actividades", style = MaterialTheme.typography.bodyMedium)

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(activities) { activityIndex ->
                val earned = starsByActivity[activityIndex]
                val unlocked = activityIndex == 0 || starsByActivity[activityIndex - 1] != null
                val status = when {
                    earned != null -> "Completada"
                    unlocked -> "Disponible"
                    else -> "Bloqueada"
                }
                val starsText = earned?.let { "$it *" } ?: "-"

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "Actividad ${activityIndex + 1}: $status | Estrellas: $starsText",
                        modifier = Modifier.weight(1f)
                    )
                    Button(
                        onClick = { onStartActivity(activityIndex) },
                        enabled = unlocked
                    ) {
                        Text("Jugar")
                    }
                }
            }
        }

        OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Volver a niveles")
        }
    }
}
