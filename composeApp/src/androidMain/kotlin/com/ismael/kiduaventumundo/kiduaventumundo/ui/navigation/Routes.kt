package com.ismael.kiduaventumundo.kiduaventumundo.ui.navigation



object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val MENU = "menu"
    const val REGISTER = "register"
    const val PROFILE = "profile"
    const val ENGLISH = "english"
    const val ENGLISH_ACTIVITIES = "english_activities/{level}"
    const val ENGLISH_LEVEL_1 = "english_level_1"
    const val ENGLISH_LEVEL_2 = "english_level_2"
    const val ENGLISH_LEVEL_3 = "english_level_3"
    const val ENGLISH_LEVEL_4 = "english_level_4"
    const val ENGLISH_LEVEL_5 = "english_level_5"
    const val ENGLISH_LEVEL_6 = "english_level_6"
    const val ENGLISH_LEVEL_7 = "english_level_7"
    const val ENGLISH_LEVEL_8 = "english_level_8"

    fun englishActivities(level: Int): String = "english_activities/$level"
}
