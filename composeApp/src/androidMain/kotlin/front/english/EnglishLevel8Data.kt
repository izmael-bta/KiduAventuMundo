package com.ismael.kiduaventumundo.kiduaventumundo.front.english

object EnglishLevel8Data {
    fun questions(): List<QuizQuestion> = listOf(
        QuizQuestion("Final: select PURPLE", "Final challenge", "purple", listOf(
            QuizOption("green", "GREEN"),
            QuizOption("purple", "PURPLE"),
            QuizOption("yellow", "YELLOW"),
            QuizOption("red", "RED")
        )),
        QuizQuestion("Final: DOG says...", "Final challenge", "woof", listOf(
            QuizOption("quack", "QUACK"),
            QuizOption("meow", "MEOW"),
            QuizOption("woof", "WOOF"),
            QuizOption("moo", "MOO")
        )),
        QuizQuestion("Final: select ORANGE HOUSE", "Final challenge", "orange_house", listOf(
            QuizOption("orange_house", "ORANGE HOUSE"),
            QuizOption("blue_house", "BLUE HOUSE"),
            QuizOption("red_house", "RED HOUSE"),
            QuizOption("green_house", "GREEN HOUSE")
        )),
        QuizQuestion("Final: select LION", "Final challenge", "lion", listOf(
            QuizOption("tiger", "TIGER"),
            QuizOption("lion", "LION"),
            QuizOption("cat", "CAT"),
            QuizOption("horse", "HORSE")
        )),
        QuizQuestion("Final: select BOOK", "Final challenge", "book", listOf(
            QuizOption("ball", "BALL"),
            QuizOption("car", "CAR"),
            QuizOption("book", "BOOK"),
            QuizOption("house", "HOUSE")
        ))
    )
}
