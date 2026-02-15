package com.ismael.kiduaventumundo.kiduaventumundo.front.english

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.EnglishManager

@Composable
fun EnglishLevel1Screen(
    onBack: () -> Unit,
    onFinished: () -> Unit
) {
    val questions = remember { EnglishLevel1Data.questions() }
    var currentIndex by remember { mutableStateOf(0) }
    var correctAnswers by remember { mutableStateOf(0) }
    var feedback by remember { mutableStateOf<String?>(null) }

    if (currentIndex !in questions.indices) {
        onFinished()
        return
    }

    val currentQuestion = questions[currentIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Nivel 1 - Colores (${currentIndex + 1}/${questions.size})",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))
        Text(
            text = currentQuestion.promptEn,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.height(12.dp))

        feedback?.let {
            Text(it, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
        }

        currentQuestion.options.forEach { option ->
            Button(
                onClick = {
                    val isCorrect = option.id == currentQuestion.correctId
                    feedback = if (isCorrect) "Correcto" else "Incorrecto"
                    if (isCorrect) correctAnswers++

                    if (currentIndex + 1 < questions.size) {
                        currentIndex++
                    } else {
                        EnglishManager.completeLevel(level = 1, starsEarned = correctAnswers)
                        onFinished()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(option.labelEn)
            }

            Spacer(Modifier.height(8.dp))
        }

        Spacer(Modifier.height(8.dp))
        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}
