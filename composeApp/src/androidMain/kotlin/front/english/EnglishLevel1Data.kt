package com.ismael.kiduaventumundo.kiduaventumundo.front.english

import androidx.compose.ui.graphics.Color

object EnglishLevel1Data {

    private val RED = ColorOption("red", "RED", Color(0xFFE53935))
    private val BLUE = ColorOption("blue", "BLUE", Color(0xFF1E88E5))
    private val YELLOW = ColorOption("yellow", "YELLOW", Color(0xFFFDD835))
    private val GREEN = ColorOption("green", "GREEN", Color(0xFF43A047))
    private val ORANGE = ColorOption("orange", "ORANGE", Color(0xFFFB8C00))
    private val PURPLE = ColorOption("purple", "PURPLE", Color(0xFF8E24AA))

    fun questions(): List<ColorQuestion> = listOf(
        ColorQuestion("Select: RED", "red", listOf(RED, BLUE, YELLOW, GREEN)),
        ColorQuestion("Select: BLUE", "blue", listOf(BLUE, GREEN, ORANGE, YELLOW)),
        ColorQuestion("Select: YELLOW", "yellow", listOf(YELLOW, PURPLE, RED, BLUE)),
        ColorQuestion("Select: GREEN", "green", listOf(GREEN, ORANGE, BLUE, RED)),
        ColorQuestion("Select: PURPLE", "purple", listOf(PURPLE, YELLOW, GREEN, BLUE))
    )
}