package com.ismael.kiduaventumundo.kiduaventumundo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ismael.kiduaventumundo.kiduaventumundo.back.data.english.EnglishLevel1Data
import com.ismael.kiduaventumundo.kiduaventumundo.back.data.english.EnglishLevel2Data
import com.ismael.kiduaventumundo.kiduaventumundo.back.data.english.EnglishLevel3Data
import com.ismael.kiduaventumundo.kiduaventumundo.back.data.english.EnglishLevel4Data
import com.ismael.kiduaventumundo.kiduaventumundo.back.data.english.EnglishLevel5Data
import com.ismael.kiduaventumundo.kiduaventumundo.back.data.english.EnglishLevel6Data
import com.ismael.kiduaventumundo.kiduaventumundo.back.data.english.EnglishLevel7Data
import com.ismael.kiduaventumundo.kiduaventumundo.back.data.english.EnglishLevel8Data
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.EnglishManager
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.auth.LoginService
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.auth.PasswordRecoveryService
import com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.EnglishMenuScreen
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.RemoteGraph
import com.ismael.kiduaventumundo.kiduaventumundo.domain.session.UserSession
import com.ismael.kiduaventumundo.kiduaventumundo.front.english.EnglishActivitiesScreen
import com.ismael.kiduaventumundo.kiduaventumundo.front.english.EnglishLevel1Screen
import com.ismael.kiduaventumundo.kiduaventumundo.front.english.EnglishLevel2Screen
import com.ismael.kiduaventumundo.kiduaventumundo.front.english.EnglishLevel3Screen
import com.ismael.kiduaventumundo.kiduaventumundo.front.english.EnglishLevel4Screen
import com.ismael.kiduaventumundo.kiduaventumundo.front.english.EnglishLevel5Screen
import com.ismael.kiduaventumundo.kiduaventumundo.front.english.EnglishLevel6Screen
import com.ismael.kiduaventumundo.kiduaventumundo.front.english.EnglishLevel7Screen
import com.ismael.kiduaventumundo.kiduaventumundo.front.english.EnglishLevel8Screen
import com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.ForgotPasswordScreen
import com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.LoginScreen
import com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.MenuScreen
import com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.ProfileScreen
import com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.ProgressScreen
import com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.RegisterScreen
import com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.SplashScreen
import com.ismael.kiduaventumundo.kiduaventumundo.ui.viewmodel.ProfileViewModel
import front.models.UserProfileUi
import kotlinx.coroutines.launch

private fun NavHostController.navigateToEnglishActivitiesSafely(level: Int) {
    val returnedToEnglishMenu = popBackStack(Routes.ENGLISH, inclusive = false)
    navigate(Routes.englishActivities(level)) {
        if (returnedToEnglishMenu) {
            popUpTo(Routes.ENGLISH) { inclusive = false }
        }
        launchSingleTop = true
    }
}

private fun NavHostController.navigateToEnglishMenuSafely() {
    val returnedToEnglishMenu = popBackStack(Routes.ENGLISH, inclusive = false)
    if (!returnedToEnglishMenu) {
        navigate(Routes.ENGLISH) {
            launchSingleTop = true
        }
    }
}

@Composable
fun AndroidApp() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    val remoteGraph = remember { RemoteGraph() }
    val userRepository = remoteGraph.userRepository
    val sessionRepository = remoteGraph.sessionRepository
    val progressRepository = remoteGraph.progressRepository
    val eventsRepository = remoteGraph.eventsRepository
    val reportsRepository = remoteGraph.reportsRepository

    val loginService = remember { LoginService(userRepository, sessionRepository) }
    val recoveryService = remember { PasswordRecoveryService(userRepository) }

    var hasSession by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        EnglishManager.configure(
            progressRepository = progressRepository,
            eventsRepository = eventsRepository,
            reportsRepository = reportsRepository
        )

        val sessionUserId = sessionRepository.getSessionUserId()
        if (sessionUserId != null) {
            val user = userRepository.getUserById(sessionUserId)
            if (user != null) {
                UserSession.setUser(user)
                hasSession = true
            }
        }
    }

    val onEnglishLevelFinished: (Int?) -> Unit = { nextLevel ->
        if (nextLevel != null) {
            navController.navigateToEnglishActivitiesSafely(nextLevel)
        } else {
            navController.navigateToEnglishMenuSafely()
        }
    }

    val profileViewModel: ProfileViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(
                hasSession = hasSession || UserSession.currentUser != null,
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

        composable(Routes.LOGIN) {
            LoginScreen(
                loginService = loginService,
                onGoRegister = { navController.navigate(Routes.REGISTER) },
                onGoForgotPassword = { navController.navigate(Routes.FORGOT_PASSWORD) },
                onLoginSuccess = {
                    hasSession = true
                    navController.navigate(Routes.MENU) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.FORGOT_PASSWORD) {
            ForgotPasswordScreen(
                recoveryService = recoveryService,
                onBackToLogin = { navController.popBackStack() }
            )
        }
        composable(Routes.REGISTER) {

            RegisterScreen(

                onRegisterSuccess = {
                    UserSession.clear()
                    hasSession = false
                    scope.launch {
                        sessionRepository.clearSession()
                    }
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.REGISTER) { inclusive = true }
                    }

                }

            )
        }

        composable(Routes.MENU) {
            val sessionUser = UserSession.currentUser

            if (sessionUser == null) {
                LaunchedEffect(Unit) {
                    EnglishManager.clearInMemoryProgress()
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.MENU) { inclusive = true }
                    }
                }
                return@composable
            }

            LaunchedEffect(sessionUser.id) {
                EnglishManager.bindUserSession(userId = sessionUser.id)
                EnglishManager.refreshSummaryFromApi(userId = sessionUser.id)
            }

            MenuScreen(
                nickname = sessionUser.nickname,
                starsCount = EnglishManager.stars.value,
                profileViewModel = profileViewModel,
                onGoEnglish = { navController.navigate(Routes.ENGLISH) },
                onGoProgress = { navController.navigate(Routes.PROGRESS) },
                onGoProfile = { navController.navigate(Routes.PROFILE) }
            )
        }

        composable(Routes.PROGRESS) {
            val progress = EnglishManager.getProgressSummary()
            ProgressScreen(
                totalStars = progress.totalStars,
                activitiesCompleted = progress.activitiesCompleted,
                totalActivities = EnglishManager.getTotalActivitiesCount().coerceAtLeast(1),
                currentLevel = progress.currentLevel,
                unlockedLevels = progress.unlockedLevels,
                onBack = { navController.popBackStack() },
            )
        }

        composable(Routes.PROFILE) {
            val sessionUser = UserSession.currentUser

            if (sessionUser == null) {
                LaunchedEffect(Unit) {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.PROFILE) { inclusive = true }
                    }
                }
                return@composable
            }

            LaunchedEffect(sessionUser.id) {
                val savedAvatarId = sessionUser.avatarId
                    .filter { it.isDigit() }
                    .toIntOrNull()

                savedAvatarId?.let { id ->
                    profileViewModel.avatars
                        .find { it.id == id }
                        ?.let { avatar -> profileViewModel.setAvatar(avatar) }
                }
            }

            ProfileScreen(
                profile = UserProfileUi(
                    name = sessionUser.name,
                    age = sessionUser.age,
                    username = sessionUser.nickname,
                    avatarId = sessionUser.avatarId.filter { it.isDigit() }.toIntOrNull() ?: 1
                ),
                avatars = profileViewModel.avatars,
                selectedAvatar = profileViewModel.selectedAvatar,
                onAvatarSelected = { avatar -> profileViewModel.setAvatar(avatar) },
                onSaveAvatar = { avatar ->
                    scope.launch {
                        val avatarId = "avatar_${avatar.id}"
                        val updated = userRepository.updateAvatar(sessionUser.id, avatarId)
                        if (updated) {
                            UserSession.setUser(sessionUser.copy(avatarId = avatarId))
                            profileViewModel.setAvatar(avatar)
                        }
                        navController.popBackStack()
                    }
                },
                onLogout = {
                    scope.launch {
                        EnglishManager.persistCurrentUserProgress()
                        EnglishManager.clearInMemoryProgress()
                        UserSession.clear()
                        hasSession = false
                        sessionRepository.clearSession()
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(0)
                        }
                    }
                }
            )
        }

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

        composable(Routes.ENGLISH_LEVEL_1) { EnglishLevel1Screen(onBack = { navController.popBackStack() }, onFinished = onEnglishLevelFinished) }
        composable(Routes.ENGLISH_LEVEL_2) { EnglishLevel2Screen(onBack = { navController.popBackStack() }, onFinished = onEnglishLevelFinished) }
        composable(Routes.ENGLISH_LEVEL_3) { EnglishLevel3Screen(onBack = { navController.popBackStack() }, onFinished = onEnglishLevelFinished) }
        composable(Routes.ENGLISH_LEVEL_4) { EnglishLevel4Screen(onBack = { navController.popBackStack() }, onFinished = onEnglishLevelFinished) }
        composable(Routes.ENGLISH_LEVEL_5) { EnglishLevel5Screen(onBack = { navController.popBackStack() }, onFinished = onEnglishLevelFinished) }
        composable(Routes.ENGLISH_LEVEL_6) { EnglishLevel6Screen(onBack = { navController.popBackStack() }, onFinished = onEnglishLevelFinished) }
        composable(Routes.ENGLISH_LEVEL_7) { EnglishLevel7Screen(onBack = { navController.popBackStack() }, onFinished = onEnglishLevelFinished) }
        composable(Routes.ENGLISH_LEVEL_8) { EnglishLevel8Screen(onBack = { navController.popBackStack() }, onFinished = onEnglishLevelFinished) }
    }
}
