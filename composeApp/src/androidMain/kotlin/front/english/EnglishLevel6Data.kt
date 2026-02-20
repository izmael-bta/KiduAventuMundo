package com.ismael.kiduaventumundo.kiduaventumundo.front.english

object EnglishLevel6Data {
    fun questions(): List<QuizQuestion> = listOf(
        QuizQuestion("CAT says...", "Match animal with sound", "meow", listOf(
            QuizOption("woof", "WOOF"),
            QuizOption("meow", "MEOW"),
            QuizOption("moo", "MOO"),
            QuizOption("quack", "QUACK")
        )),
        QuizQuestion("DOG says...", "Match animal with sound", "woof", listOf(
            QuizOption("meow", "MEOW"),
            QuizOption("woof", "WOOF"),
            QuizOption("tweet", "TWEET"),
            QuizOption("moo", "MOO")
        )),
        QuizQuestion("DUCK says...", "Match animal with sound", "quack", listOf(
            QuizOption("neigh", "NEIGH"),
            QuizOption("woof", "WOOF"),
            QuizOption("quack", "QUACK"),
            QuizOption("croak", "CROAK")
        )),
        QuizQuestion("COW says...", "Match animal with sound", "moo", listOf(
            QuizOption("moo", "MOO"),
            QuizOption("baa", "BAA"),
            QuizOption("meow", "MEOW"),
            QuizOption("tweet", "TWEET")
        )),
        QuizQuestion("HORSE says...", "Match animal with sound", "neigh", listOf(
            QuizOption("neigh", "NEIGH"),
            QuizOption("quack", "QUACK"),
            QuizOption("woof", "WOOF"),
            QuizOption("moo", "MOO")
        ))
    )
}
