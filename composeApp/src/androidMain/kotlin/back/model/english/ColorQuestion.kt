package com.ismael.kiduaventumundo.kiduaventumundo.back.model.english

import androidx.compose.ui.graphics.Color

/**
 * Opcion de respuesta para preguntas de colores.
 */
data class ColorOption(
    val id: String,
    val labelEn: String,
    val color: Color
)

/**
 * Pregunta base para el nivel de colores.
 */
data class ColorQuestion(
    val promptEn: String,
    val correctId: String,
    val options: List<ColorOption>
)
