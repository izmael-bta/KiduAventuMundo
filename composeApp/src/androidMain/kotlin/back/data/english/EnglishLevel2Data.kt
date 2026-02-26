package com.ismael.kiduaventumundo.kiduaventumundo.back.data.english

data class ObjectOption(
    val id: String,
    val labelEn: String,
    val emoji: String
)

data class ObjectQuestion(
    val promptEn: String,
    val correctId: String,
    val options: List<ObjectOption>
)

object EnglishLevel2Data {

    private val APPLE = ObjectOption("apple", "APPLE", "\uD83C\uDF4E")
    private val BALL = ObjectOption("ball", "BALL", "\u26BD")
    private val BOOK = ObjectOption("book", "BOOK", "\uD83D\uDCD6")
    private val CAR = ObjectOption("car", "CAR", "\uD83D\uDE97")
    private val HOUSE = ObjectOption("house", "HOUSE", "\uD83C\uDFE0")
    private val STAR = ObjectOption("star", "STAR", "\u2B50")
    private val CAT = ObjectOption("cat", "CAT", "\uD83D\uDC31")
    private val DOG = ObjectOption("dog", "DOG", "\uD83D\uDC36")

    fun questions(): List<ObjectQuestion> = listOf(
        ObjectQuestion("Select: APPLE", "apple", listOf(APPLE, BALL, BOOK, CAR)),
        ObjectQuestion("Select: BALL", "ball", listOf(HOUSE, BALL, STAR, DOG)),
        ObjectQuestion("Select: BOOK", "book", listOf(CAT, CAR, BOOK, APPLE)),
        ObjectQuestion("Select: HOUSE", "house", listOf(BOOK, STAR, HOUSE, BALL)),
        ObjectQuestion("Select: DOG", "dog", listOf(DOG, CAT, CAR, APPLE))
    )
}
