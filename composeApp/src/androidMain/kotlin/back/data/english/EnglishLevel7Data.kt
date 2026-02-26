package com.ismael.kiduaventumundo.kiduaventumundo.back.data.english

import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english.QuizOption
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english.QuizQuestion

object EnglishLevel7Data {
    fun questions(): List<QuizQuestion> = listOf(
        QuizQuestion("Select the color: BLUE", "Mixed review", "blue", listOf(
            QuizOption("red", "RED"),
            QuizOption("blue", "BLUE"),
            QuizOption("green", "GREEN"),
            QuizOption("yellow", "YELLOW")
        )),
        QuizQuestion("Select the object: BOOK", "Mixed review", "book", listOf(
            QuizOption("book", "BOOK"),
            QuizOption("car", "CAR"),
            QuizOption("house", "HOUSE"),
            QuizOption("ball", "BALL")
        )),
        QuizQuestion("Select the animal: BIRD", "Mixed review", "bird", listOf(
            QuizOption("cat", "CAT"),
            QuizOption("dog", "DOG"),
            QuizOption("bird", "BIRD"),
            QuizOption("fish", "FISH")
        )),
        QuizQuestion("Which sound is: QUACK?", "Mixed review", "quack", listOf(
            QuizOption("quack", "QUACK"),
            QuizOption("woof", "WOOF"),
            QuizOption("meow", "MEOW"),
            QuizOption("moo", "MOO")
        )),
        QuizQuestion("Select: GREEN STAR", "Mixed review", "green_star", listOf(
            QuizOption("green_star", "GREEN STAR"),
            QuizOption("blue_star", "BLUE STAR"),
            QuizOption("red_star", "RED STAR"),
            QuizOption("yellow_star", "YELLOW STAR")
        ))
    )
}
