package com.ismael.kiduaventumundo.kiduaventumundo.back.data.english

import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english.QuizOption
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english.QuizQuestion

/**
 * Banco de preguntas del nivel 4 (sonidos).
 */
object EnglishLevel4Data {
    fun questions(): List<QuizQuestion> = listOf(
        QuizQuestion("Which animal says: MEOW?", "Select the matching sound", "meow", listOf(
            QuizOption("woof", "WOOF"),
            QuizOption("meow", "MEOW"),
            QuizOption("moo", "MOO"),
            QuizOption("quack", "QUACK")
        )),
        QuizQuestion("Which animal says: WOOF?", "Select the matching sound", "woof", listOf(
            QuizOption("woof", "WOOF"),
            QuizOption("tweet", "TWEET"),
            QuizOption("croak", "CROAK"),
            QuizOption("neigh", "NEIGH")
        )),
        QuizQuestion("Which animal says: QUACK?", "Select the matching sound", "quack", listOf(
            QuizOption("meow", "MEOW"),
            QuizOption("moo", "MOO"),
            QuizOption("quack", "QUACK"),
            QuizOption("woof", "WOOF")
        )),
        QuizQuestion("Which animal says: MOO?", "Select the matching sound", "moo", listOf(
            QuizOption("moo", "MOO"),
            QuizOption("baa", "BAA"),
            QuizOption("tweet", "TWEET"),
            QuizOption("meow", "MEOW")
        )),
        QuizQuestion("Which animal says: NEIGH?", "Select the matching sound", "neigh", listOf(
            QuizOption("croak", "CROAK"),
            QuizOption("neigh", "NEIGH"),
            QuizOption("woof", "WOOF"),
            QuizOption("quack", "QUACK")
        ))
    )
}
