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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ismael.kiduaventumundo.kiduaventumundo.back.data.english.EnglishLevel2Data
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english.DialogConfirmAction
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english.EnglishLevelSession
import com.ismael.kiduaventumundo.kiduaventumundo.ui.components.CompleteCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EnglishLevel2Screen(
    onBack: () -> Unit,
    onFinished: (Int?) -> Unit
) {
    val questions = remember { EnglishLevel2Data.questions() }
    val session = remember { EnglishLevelSession(level = 2, totalActivities = questions.size) }
    var state = remember { mutableStateOf(session.state) }
    val scope = rememberCoroutineScope()

    val current = questions[state.value.index]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Nivel 2: Objetos", style = MaterialTheme.typography.headlineSmall)
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
                Text("Tap the correct object", style = MaterialTheme.typography.bodyMedium)
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
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    row.forEach { opt ->
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(84.dp)
                                .clickable(enabled = !state.value.locked) {
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
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(opt.emoji, style = MaterialTheme.typography.headlineSmall)
                                Spacer(Modifier.height(4.dp))
                                Text(opt.labelEn, fontWeight = FontWeight.Bold)
                            }
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            CompleteCard(
                stars = if (state.value.passed) 3 else 1,
                totalPoints = state.value.starsLevel * 10,
                onContinue = {
                    val action = session.confirmDialog()
                    state.value = session.state
                    if (action == DialogConfirmAction.CONTINUE) {
                        onFinished(session.consumeNextLevelAfterCompletion())
                    }
                }
            )
        }
    }
}
