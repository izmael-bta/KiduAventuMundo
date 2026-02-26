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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ismael.kiduaventumundo.kiduaventumundo.back.data.english.EnglishLevel1Data
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english.DialogConfirmAction
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english.EnglishLevelSession
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EnglishLevel1Screen(
    onBack: () -> Unit,
    onFinished: () -> Unit
) {
    val questions = remember { EnglishLevel1Data.questions() }
    val session = remember { EnglishLevelSession(level = 1, totalActivities = questions.size) }
    var state = remember { mutableStateOf(session.state) }
    val scope = rememberCoroutineScope()

    val current = questions[state.value.index]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Nivel 1: Colores", style = MaterialTheme.typography.headlineSmall)
        Text("* ${state.value.starsLevel} / ${state.value.passStars}", style = MaterialTheme.typography.titleMedium)
        Text(
            "Actividad ${state.value.index + 1} / ${state.value.totalActivities}",
            style = MaterialTheme.typography.bodyMedium
        )

        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    current.promptEn,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(Modifier.height(6.dp))
                Text("Tap the correct color", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(6.dp))
                Text(
                    "Errores en esta actividad: ${state.value.mistakes}",
                    style = MaterialTheme.typography.bodySmall
                )
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
                                    if (!state.value.locked) {
                                        Modifier.clickable {
                                            val result = session.submitSelection(
                                                selectedId = opt.id,
                                                correctId = current.correctId
                                            )
                                            state.value = result.state

                                            if (result.isCorrect) {
                                                scope.launch {
                                                    delay(650)
                                                    state.value = session.advanceAfterCorrect()
                                                }
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

        state.value.feedback?.let {
            Text(it, style = MaterialTheme.typography.titleMedium)
        }

        Spacer(Modifier.weight(1f))

        OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Volver")
        }
    }

    if (state.value.showEndDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text(if (state.value.passed) "Nivel completado" else "Casi") },
            text = {
                if (state.value.passed) {
                    Text("Ganaste ${state.value.starsLevel} estrellas. Se desbloqueo el Nivel 2.")
                } else {
                    Text(
                        "Ganaste ${state.value.starsLevel} estrellas. Necesitas ${state.value.passStars} para pasar."
                    )
                }
            },
            confirmButton = {
                if (state.value.passed) {
                    Button(onClick = {
                        val action = session.confirmDialog()
                        state.value = session.state
                        if (action == DialogConfirmAction.CONTINUE) onFinished()
                    }) {
                        Text("Continuar")
                    }
                } else {
                    Button(onClick = {
                        val action = session.confirmDialog()
                        if (action == DialogConfirmAction.RETRY) {
                            state.value = session.state
                        }
                    }) {
                        Text("Reintentar")
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    state.value = session.closeDialog()
                    onBack()
                }) {
                    Text("Salir")
                }
            }
        )
    }
}
