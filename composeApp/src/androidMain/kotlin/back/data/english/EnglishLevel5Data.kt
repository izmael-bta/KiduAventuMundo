package com.ismael.kiduaventumundo.kiduaventumundo.back.data.english

import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english.QuizOption
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english.QuizQuestion

object EnglishLevel5Data {
    fun questions(): List<QuizQuestion> = listOf(
        QuizQuestion("Select: GREEN BALL", "Choose the correct combination", "green_ball", listOf(
            QuizOption("red_ball", "RED BALL"),
            QuizOption("green_ball", "GREEN BALL"),
            QuizOption("blue_book", "BLUE BOOK"),
            QuizOption("yellow_car", "YELLOW CAR")
        )),
        QuizQuestion("Select: BLUE BOOK", "Choose the correct combination", "blue_book", listOf(
            QuizOption("green_house", "GREEN HOUSE"),
            QuizOption("blue_book", "BLUE BOOK"),
            QuizOption("orange_ball", "ORANGE BALL"),
            QuizOption("purple_car", "PURPLE CAR")
        )),
        QuizQuestion("Select: YELLOW CAR", "Choose the correct combination", "yellow_car", listOf(
            QuizOption("yellow_car", "YELLOW CAR"),
            QuizOption("blue_ball", "BLUE BALL"),
            QuizOption("red_book", "RED BOOK"),
            QuizOption("green_star", "GREEN STAR")
        )),
        QuizQuestion("Select: ORANGE HOUSE", "Choose the correct combination", "orange_house", listOf(
            QuizOption("orange_house", "ORANGE HOUSE"),
            QuizOption("purple_house", "PURPLE HOUSE"),
            QuizOption("yellow_book", "YELLOW BOOK"),
            QuizOption("green_car", "GREEN CAR")
        )),
        QuizQuestion("Select: PURPLE STAR", "Choose the correct combination", "purple_star", listOf(
            QuizOption("red_star", "RED STAR"),
            QuizOption("blue_star", "BLUE STAR"),
            QuizOption("purple_star", "PURPLE STAR"),
            QuizOption("green_star", "GREEN STAR")
        ))
    )
}
