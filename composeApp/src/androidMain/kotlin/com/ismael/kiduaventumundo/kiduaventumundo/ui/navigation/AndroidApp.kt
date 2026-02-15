package com.ismael.kiduaventumundo.kiduaventumundo

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ismael.kiduaventumundo.kiduaventumundo.back.db.AppDatabaseHelper
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.EnglishManager
import com.ismael.kiduaventumundo.kiduaventumundo.back.model.User
import com.ismael.kiduaventumundo.kiduaventumundo.front.english.EnglishLevel1Screen
import com.ismael.kiduaventumundo.kiduaventumundo.front.english.EnglishLevel2Screen
import com.ismael.kiduaventumundo.kiduaventumundo.front.models.DefaultAvatars
import com.ismael.kiduaventumundo.kiduaventumundo.front.models.UserProfileUi
import com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.EnglishMenuScreen
import com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.LoginScreen
import com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.MenuScreen
import com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.ProfileScreen
import com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.RegisterScreen
import com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.SplashScreen

object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val MENU = "menu"
    const val PROFILE = "profile"
    const val ENGLISH = "english"
    const val ENGLISH_LEVEL_1 = "english_level_1"
    const val ENGLISH_LEVEL_2 = "english_level_2"
}

@Composable
fun AndroidApp() {
    val context = LocalContext.current
    val db = remember { AppDatabaseHelper(context) }
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {

        // ---------------- SPLASH ----------------
        composable(Routes.SPLASH) {
            SplashScreen(
                hasSession = db.getSessionUserId() != null,
                onGoLogin = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                },
                onGoMenu = {
                    navController.navigate(Routes.MENU) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        // ---------------- LOGIN ----------------
        composable(Routes.LOGIN) {
            LoginScreen(
                onGoRegister = { navController.navigate(Routes.REGISTER) },
                onLoginSuccess = {
                    navController.navigate(Routes.MENU) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // ---------------- REGISTER ----------------
        composable(Routes.REGISTER) {
            RegisterScreen(
                avatars = DefaultAvatars,
                onCreate = { profile ->
                    if (db.nicknameExists(profile.username.trim())) {
                        return@RegisterScreen "Ese nickname ya existe."
                    }
                    val newId = db.registerUser(
                        User(
                            name = profile.name.trim(),
                            age = profile.age,
                            nickname = profile.username.trim(),
                            avatarId = "avatar_${profile.avatarId}",
                            stars = 0
                        )
                    )
                    if (newId == -1L) return@RegisterScreen "No se pudo crear la cuenta."

                    db.setSession(newId)
                    EnglishManager.resetProgress()
                    navController.navigate(Routes.MENU) {
                        popUpTo(Routes.REGISTER) { inclusive = true }
                    }
                    null
                },
                onGoLogin = { navController.popBackStack() }
            )
        }

        // ---------------- MENU ----------------
        composable(Routes.MENU) {
            val sessionUserId = db.getSessionUserId()
            val sessionUser = sessionUserId?.let { db.getUserById(it) }

            if (sessionUser == null) {
                LaunchedEffect(Unit) {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.MENU) { inclusive = true }
                    }
                }
                return@composable
            }

            MenuScreen(
                nickname = sessionUser.nickname,
                onGoEnglish = { navController.navigate(Routes.ENGLISH) },
                onGoProfile = { navController.navigate(Routes.PROFILE) },
                onLogout = {
                    db.clearSession()
                    EnglishManager.resetProgress()
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.MENU) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.PROFILE) {
            val sessionUserId = db.getSessionUserId()
            val sessionUser = sessionUserId?.let { db.getUserById(it) }

            if (sessionUser == null) {
                LaunchedEffect(Unit) {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.PROFILE) { inclusive = true }
                    }
                }
                return@composable
            }

            ProfileScreen(
                avatars = DefaultAvatars,
                profile = UserProfileUi(
                    name = sessionUser.name,
                    age = sessionUser.age,
                    username = sessionUser.nickname,
                    avatarId = sessionUser.avatarId.filter { it.isDigit() }.toIntOrNull() ?: 1
                ),
                onSave = { updatedProfile ->
                    db.updateUserAvatar(
                        userId = sessionUser.id,
                        avatarId = "avatar_${updatedProfile.avatarId}"
                    )
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() },
            )
        }

        // ---------------- ENGLISH LEVEL MENU ----------------
        composable(Routes.ENGLISH) {
            val levels = EnglishManager.getLevels()

            EnglishMenuScreen(
                levels = levels,
                onBack = { navController.popBackStack() },
                onLevelClick = { level ->
                    when (level) {
                        1 -> navController.navigate(Routes.ENGLISH_LEVEL_1)
                        2 -> navController.navigate(Routes.ENGLISH_LEVEL_2)
                    }
                }
            )
        }
        composable(Routes.ENGLISH_LEVEL_1) {
            EnglishLevel1Screen(
                onBack = { navController.popBackStack() },
                onFinished = {
                    navController.popBackStack()
                }
            )
        }
        composable(Routes.ENGLISH_LEVEL_2) {
            EnglishLevel2Screen(
                onBack = { navController.popBackStack() },
                onFinished = { navController.popBackStack() }
            )
        }
    }
}
