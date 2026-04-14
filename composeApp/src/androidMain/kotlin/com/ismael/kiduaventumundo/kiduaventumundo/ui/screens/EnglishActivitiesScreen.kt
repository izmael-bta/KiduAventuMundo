package com.ismael.kiduaventumundo.kiduaventumundo.front.english

import android.R.attr.duration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ismael.kiduaventumundo.kiduaventumundo.R
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english.ActivityStatus
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english.EnglishActivitiesUseCase
import com.ismael.kiduaventumundo.kiduaventumundo.ui.components.AnimatedCloud

@Composable
fun EnglishActivitiesScreen(
    level: Int,
    levelTitle: String,
    totalActivities: Int,
    onBack: () -> Unit,
    onStartActivity: (Int) -> Unit
) {
    val activities = EnglishActivitiesUseCase.getActivities(level, totalActivities)
    val cloudCount = 6

    Box(
        modifier = Modifier.fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF03E0F4), Color(0xFF02A7BD))))
    )
    Box(modifier = Modifier.fillMaxSize()){

        AnimatedCloud(drawableRes = R.drawable.cloud,
            startX = (-800..0).random().toFloat(),
            yOffset = (50..300).random().toFloat(),
            size = (100..200).random().toFloat(),
            duration = (20000..40000).random()
        ) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Nivel $level: $levelTitle",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(activities) { activity ->
                val isEnabled = activity.status != ActivityStatus.BLOCKED
                val statusText = when (activity.status) {
                    ActivityStatus.COMPLETED -> "Completado"
                    ActivityStatus.AVAILABLE -> "Disponible"
                    ActivityStatus.BLOCKED -> "Bloqueado"
                }
                val actionText = when (activity.status) {
                    ActivityStatus.COMPLETED -> "✔"
                    ActivityStatus.AVAILABLE -> "🏁"
                    ActivityStatus.BLOCKED -> "🔒"
                }
                val starsText = activity.earnedStars?.let { " | $it estrellas" } ?: ""

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = isEnabled) {
                            onStartActivity(activity.activityIndex)
                        },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = when (activity.status) {
                            ActivityStatus.COMPLETED -> Color(0xFFE8F5E9)
                            ActivityStatus.AVAILABLE -> Color.White
                            ActivityStatus.BLOCKED -> Color(0xFFF3F3F3)
                        }
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = if (isEnabled) 6.dp else 1.dp
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Actividad ${activity.activityIndex + 1}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = statusText + starsText,
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (isEnabled) Color(0xFF37474F) else Color(0xFF9E9E9E)
                            )
                        }

                        Text(
                            text = actionText,
                            style = MaterialTheme.typography.labelLarge,
                            color = if (isEnabled) Color(0xFF00695C) else Color(0xFF9E9E9E)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            OutlinedButton(onClick = onBack, modifier = Modifier
                .width(160.dp) .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xD2AD2605))
            ) {
                Text("Volver", fontWeight = FontWeight.Bold, color = Color.White)
            }
            /*OutlinedButton(
                onClick = onBack,
                modifier = Modifier.width(200.dp),
                border = BorderStroke(2.dp, Color.Red),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
            ) {
                Text("Volver a niveles", fontWeight = FontWeight.Bold)
            }*/
        }
    }
}
