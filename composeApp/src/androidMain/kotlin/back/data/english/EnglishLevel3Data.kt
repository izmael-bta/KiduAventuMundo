package com.ismael.kiduaventumundo.kiduaventumundo.back.data.english

import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english.QuizOption
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english.QuizQuestion

object EnglishLevel3Data {
    fun questions(): List<QuizQuestion> = listOf(
        QuizQuestion("Select: CAT", "Tap the correct animal", "cat", listOf(
            QuizOption("cat", "CAT"),
            QuizOption("dog", "DOG"),
            QuizOption("bird", "BIRD"),
            QuizOption("fish", "FISH")
        )),
        QuizQuestion("Select: DOG", "Tap the correct animal", "dog", listOf(
            QuizOption("lion", "LION"),
            QuizOption("dog", "DOG"),
            QuizOption("cow", "COW"),
            QuizOption("horse", "HORSE")
        )),
        QuizQuestion("Select: BIRD", "Tap the correct animal", "bird", listOf(
            QuizOption("cat", "CAT"),
            QuizOption("bird", "BIRD"),
            QuizOption("frog", "FROG"),
            QuizOption("bear", "BEAR")
        )),
        QuizQuestion("Select: LION", "Tap the correct animal", "lion", listOf(
            QuizOption("tiger", "TIGER"),
            QuizOption("rabbit", "RABBIT"),
            QuizOption("lion", "LION"),
            QuizOption("duck", "DUCK")
        )),
        QuizQuestion("Select: FISH", "Tap the correct animal", "fish", listOf(
            QuizOption("fish", "FISH"),
            QuizOption("bird", "BIRD"),
            QuizOption("cow", "COW"),
            QuizOption("dog", "DOG")
        ))
    )
}
