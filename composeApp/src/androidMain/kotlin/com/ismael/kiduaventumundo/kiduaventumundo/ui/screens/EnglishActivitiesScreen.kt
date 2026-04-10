package com.ismael.kiduaventumundo.kiduaventumundo.front.english

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english.ActivityStatus
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english.EnglishActivitiesUseCase
import com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.domain.actions.EnglishManager

@Composable
fun EnglishActivitiesScreen(
    level: Int,
    levelTitle: String,
    totalActivities: Int,
    onBack: () -> Unit,
    onStartActivity: (Int) -> Unit
) {
    // 1. Aquí se crea la variable (No la muevas de aquí)
    val starsByActivity = EnglishManager.getActivityStars(level, totalActivities)
    val activities = (0 until totalActivities).toList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Título Centrado
        Text(
            text = "Nivel $level: $levelTitle",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center, // Centrado superior
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )

        // Tu LazyColumn actual con las actividades
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(activities) { activityIndex ->
                // Ccódigo de Row y Button
            }
        }

        // --- AQUÍ VA LA LÓGICA DE DESBLOQUEO (Dentro de la función) ---

        // Verificamos si todas las actividades tienen estrellas
        /*val allActivitiesDone = starsByActivity.all { it != null }
        val totalStarsEarned = starsByActivity.filterNotNull().sum()

        if (allActivitiesDone) {
            // Esto desbloquea el nivel 2 en el EnglishManager
            EnglishManager.completeLevel(level = level, starsEarned = totalStarsEarned)
        } */

        Spacer(modifier = Modifier.height(16.dp))

        // Botón "Volver" pequeño, rojo y centrado
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.width(200.dp),
                border = BorderStroke(2.dp, Color.Red),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
            ) {
                Text("Volver a niveles", fontWeight = FontWeight.Bold)
            }
        }
    } // Aquí cierra el Column
} // Aquí cierra la función
