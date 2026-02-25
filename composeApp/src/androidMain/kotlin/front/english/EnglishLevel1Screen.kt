package com.ismael.kiduaventumundo.kiduaventumundo.front.english

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ismael.kiduaventumundo.kiduaventumundo.back.data.english.EnglishLevel1Data
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.EnglishManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EnglishLevel1Screen(
    onBack: () -> Unit,
    onFinished: () -> Unit
) {
    val passStars = 13
    val questions = remember { EnglishLevel1Data.questions() }
    val totalActivities = questions.size
    val startIndex = remember {
        EnglishManager.consumeStartActivity(level = 1).coerceIn(0, questions.lastIndex)
    }

    var index by remember { mutableIntStateOf(startIndex) }
    var starsLevel by remember { mutableIntStateOf(EnglishManager.getLevelStars(1, totalActivities)) }
    var mistakes by remember { mutableIntStateOf(0) }
    val activityStars = remember {
        mutableStateListOf<Int?>().apply {
            addAll(EnglishManager.getActivityStars(level = 1, totalActivities = totalActivities))
        }
    }

    var feedback by remember { mutableStateOf<String?>(null) }
    var locked by remember { mutableStateOf(false) }

    var showEndDialog by remember { mutableStateOf(false) }
    var passed by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val current = questions[index]

    fun earnedStarsForThisActivity(m: Int): Int = when {
        m == 0 -> 3
        m == 1 -> 2
        m == 2 -> 1
        else -> 0
    }

    fun restartLevel() {
        index = 0
        starsLevel = activityStars.sumOf { it ?: 0 }
        mistakes = 0
        feedback = null
        locked = false
        showEndDialog = false
        passed = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Nivel 1: Colores", style = MaterialTheme.typography.headlineSmall)
        Text("* $starsLevel / $passStars", style = MaterialTheme.typography.titleMedium)
        Text("Actividad ${index + 1} / ${questions.size}", style = MaterialTheme.typography.bodyMedium)

        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    current.promptEn,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(Modifier.height(6.dp))
                Text("Tap the correct color", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(6.dp))
                Text("Errores en esta actividad: $mistakes", style = MaterialTheme.typography.bodySmall)
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            current.options.chunked(2).forEach { row ->
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    row.forEach { opt ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(70.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(opt.color)
                                .then(
                                    if (!locked) {
                                        Modifier.clickable {
                                            val ok = opt.id == current.correctId

                                            if (ok) {
                                                locked = true
                                                val earned = earnedStarsForThisActivity(mistakes)
                                                EnglishManager.recordActivityResult(
                                                    level = 1,
                                                    activityIndex = index,
                                                    starsEarned = earned,
                                                    totalActivities = totalActivities
                                                )
                                                activityStars[index] = maxOf(activityStars[index] ?: -1, earned)
                                                starsLevel = activityStars.sumOf { it ?: 0 }
                                                feedback = "+$earned *"

                                                scope.launch {
                                                    delay(650)

                                                    val isLast = index == questions.lastIndex
                                                    if (!isLast) {
                                                        index++
                                                        mistakes = 0
                                                        feedback = null
                                                        locked = false
                                                    } else {
                                                        passed = starsLevel >= passStars
                                                        showEndDialog = true
                                                    }
                                                }
                                            } else {
                                                mistakes++
                                                feedback = "Intenta otra vez"
                                            }
                                        }
                                    } else {
                                        Modifier
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                opt.labelEn,
                                color = androidx.compose.ui.graphics.Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        feedback?.let {
            Text(it, style = MaterialTheme.typography.titleMedium)
        }

        Spacer(Modifier.weight(1f))

        OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Volver")
        }
    }

    if (showEndDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text(if (passed) "Nivel completado" else "Casi") },
            text = {
                if (passed) {
                    Text("Ganaste $starsLevel estrellas. Se desbloqueo el Nivel 2.")
                } else {
                    Text("Ganaste $starsLevel estrellas. Necesitas $passStars para pasar.")
                }
            },
            confirmButton = {
                if (passed) {
                    Button(onClick = {
                        EnglishManager.completeLevel(level = 1, starsEarned = starsLevel)
                        showEndDialog = false
                        onFinished()
                    }) {
                        Text("Continuar")
                    }
                } else {
                    Button(onClick = { restartLevel() }) {
                        Text("Reintentar")
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showEndDialog = false
                    onBack()
                }) {
                    Text("Salir")
                }
            }
        )
    }
}
