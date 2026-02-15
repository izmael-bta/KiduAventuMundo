package com.ismael.kiduaventumundo.kiduaventumundo.front.english

import androidx.compose.ui.graphics.Color

data class ColorOption(
    val id: String,
    val labelEn: String,
    val color: Color
)

data class ColorQuestion(
    val promptEn: String,
    val correctId: String,
    val options: List<ColorOption>
)