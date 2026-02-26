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
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english.ActivityStatus
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english.EnglishActivitiesUseCase

@Composable
fun EnglishActivitiesScreen(
    level: Int,
    levelTitle: String,
    totalActivities: Int,
    onBack: () -> Unit,
    onStartActivity: (Int) -> Unit
) {
    val activities = EnglishActivitiesUseCase.getActivities(level, totalActivities)

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
            items(activities) { activity ->
                val status = when (activity.status) {
                    ActivityStatus.COMPLETED -> "Completada"
                    ActivityStatus.AVAILABLE -> "Disponible"
                    ActivityStatus.BLOCKED -> "Bloqueada"
                }
                val starsText = activity.earnedStars?.let { "$it *" } ?: "-"
                val unlocked = activity.status != ActivityStatus.BLOCKED

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "Actividad ${activity.activityIndex + 1}: $status | Estrellas: $starsText",
                        modifier = Modifier.weight(1f)
                    )
                    Button(
                        onClick = { onStartActivity(activity.activityIndex) },
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
