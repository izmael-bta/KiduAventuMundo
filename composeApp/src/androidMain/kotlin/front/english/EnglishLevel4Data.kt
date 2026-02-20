package com.ismael.kiduaventumundo.kiduaventumundo.front.english

object EnglishLevel4Data {
    fun questions(): List<QuizQuestion> = listOf(
        QuizQuestion("Which sound says: MEOW?", "Select the matching sound", "meow", listOf(
            QuizOption("woof", "WOOF"),
            QuizOption("meow", "MEOW"),
            QuizOption("moo", "MOO"),
            QuizOption("quack", "QUACK")
        )),
        QuizQuestion("Which sound says: WOOF?", "Select the matching sound", "woof", listOf(
            QuizOption("woof", "WOOF"),
            QuizOption("tweet", "TWEET"),
            QuizOption("croak", "CROAK"),
            QuizOption("neigh", "NEIGH")
        )),
        QuizQuestion("Which sound says: QUACK?", "Select the matching sound", "quack", listOf(
            QuizOption("meow", "MEOW"),
            QuizOption("moo", "MOO"),
            QuizOption("quack", "QUACK"),
            QuizOption("woof", "WOOF")
        )),
        QuizQuestion("Which sound says: MOO?", "Select the matching sound", "moo", listOf(
            QuizOption("moo", "MOO"),
            QuizOption("baa", "BAA"),
            QuizOption("tweet", "TWEET"),
            QuizOption("meow", "MEOW")
        )),
        QuizQuestion("Which sound says: NEIGH?", "Select the matching sound", "neigh", listOf(
            QuizOption("croak", "CROAK"),
            QuizOption("neigh", "NEIGH"),
            QuizOption("woof", "WOOF"),
            QuizOption("quack", "QUACK")
        ))
    )
}
