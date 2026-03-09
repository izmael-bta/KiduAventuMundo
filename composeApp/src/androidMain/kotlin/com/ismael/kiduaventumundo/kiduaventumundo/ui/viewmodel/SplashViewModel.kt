package com.ismael.kiduaventumundo.kiduaventumundo.ui.viewmodel

import com.ismael.kiduaventumundo.kiduaventumundo.domain.session.UserSession

class SplashViewModel {
    fun hasSession(): Boolean {
        return UserSession.currentUser != null
    }
}
