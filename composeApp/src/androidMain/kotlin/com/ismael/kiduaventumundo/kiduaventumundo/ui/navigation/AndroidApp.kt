package com.ismael.kiduaventumundo.kiduaventumundo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ismael.kiduaventumundo.kiduaventumundo.back.db.AppDatabaseHelper
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.EnglishManager
import com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.EnglishMenuScreen
import com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.LoginScreen
import com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.MenuScreen
import com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.ProfileScreen
import com.ismael.kiduaventumundo.kiduaventumundo.datasource.repository.UserRepositoryImpl
import com.ismael.kiduaventumundo.kiduaventumundo.domain.operations.RegistrarUsuario
import com.ismael.kiduaventumundo.kiduaventumundo.front.english.*
import com.ismael.kiduaventumundo.kiduaventumundo.front.models.DefaultAvatars
import com.ismael.kiduaventumundo.kiduaventumundo.front.models.UserProfileUi
import com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.RegisterScreen
import com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.SplashScreen





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

            val repository = UserRepositoryImpl(db)
            val registrarUsuario = RegistrarUsuario(repository)

            RegisterScreen(
                registrarUsuario = registrarUsuario,
                onRegisterSuccess = {
                    navController.navigate(Routes.MENU) {
                        popUpTo(Routes.REGISTER) { inclusive = true }
                    }
                }
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
                println("SESSION ID EN MENU: ${db.getSessionUserId()}")

            }

            MenuScreen(
                nickname = sessionUser.nickname,
                onGoEnglish = { navController.navigate(Routes.ENGLISH) },
                onGoProfile = { navController.navigate(Routes.PROFILE) }
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
                onLogout = {
                    db.clearSession()
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.MENU) { inclusive = true }
                        popUpTo(Routes.PROFILE) { inclusive = true }
                    }
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
                onLevelClick = { level -> navController.navigate(Routes.englishActivities(level)) }
            )
        }
        composable(Routes.ENGLISH_ACTIVITIES) { backStackEntry ->
            val level = backStackEntry.arguments?.getString("level")?.toIntOrNull() ?: 1
            val levelTitle = EnglishManager.getLevels().firstOrNull { it.level == level }?.title ?: "Nivel $level"
            val totalActivities = when (level) {
                1 -> EnglishLevel1Data.questions().size
                2 -> EnglishLevel2Data.questions().size
                3 -> EnglishLevel3Data.questions().size
                4 -> EnglishLevel4Data.questions().size
                5 -> EnglishLevel5Data.questions().size
                6 -> EnglishLevel6Data.questions().size
                7 -> EnglishLevel7Data.questions().size
                8 -> EnglishLevel8Data.questions().size
                else -> 5
            }

            EnglishActivitiesScreen(
                level = level,
                levelTitle = levelTitle,
                totalActivities = totalActivities,
                onBack = { navController.popBackStack() },
                onStartActivity = { activityIndex ->
                    EnglishManager.setStartActivity(level, activityIndex)
                    when (level) {
                        1 -> navController.navigate(Routes.ENGLISH_LEVEL_1)
                        2 -> navController.navigate(Routes.ENGLISH_LEVEL_2)
                        3 -> navController.navigate(Routes.ENGLISH_LEVEL_3)
                        4 -> navController.navigate(Routes.ENGLISH_LEVEL_4)
                        5 -> navController.navigate(Routes.ENGLISH_LEVEL_5)
                        6 -> navController.navigate(Routes.ENGLISH_LEVEL_6)
                        7 -> navController.navigate(Routes.ENGLISH_LEVEL_7)
                        8 -> navController.navigate(Routes.ENGLISH_LEVEL_8)
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
                onFinished = {
                    navController.popBackStack()
                }
            )
        }
        composable(Routes.ENGLISH_LEVEL_3) {
            EnglishLevel3Screen(
                onBack = { navController.popBackStack() },
                onFinished = { navController.popBackStack() }
            )
        }
        composable(Routes.ENGLISH_LEVEL_4) {
            EnglishLevel4Screen(
                onBack = { navController.popBackStack() },
                onFinished = { navController.popBackStack() }
            )
        }
        composable(Routes.ENGLISH_LEVEL_5) {
            EnglishLevel5Screen(
                onBack = { navController.popBackStack() },
                onFinished = { navController.popBackStack() }
            )
        }
        composable(Routes.ENGLISH_LEVEL_6) {
            EnglishLevel6Screen(
                onBack = { navController.popBackStack() },
                onFinished = { navController.popBackStack() }
            )
        }
        composable(Routes.ENGLISH_LEVEL_7) {
            EnglishLevel7Screen(
                onBack = { navController.popBackStack() },
                onFinished = { navController.popBackStack() }
            )
        }
        composable(Routes.ENGLISH_LEVEL_8) {
            EnglishLevel8Screen(
                onBack = { navController.popBackStack() },
                onFinished = { navController.popBackStack() }
            )
        }
    }
}

