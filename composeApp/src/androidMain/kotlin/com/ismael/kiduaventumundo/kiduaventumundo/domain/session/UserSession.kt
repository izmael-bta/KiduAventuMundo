package com.ismael.kiduaventumundo.kiduaventumundo.domain.session

import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.User

object UserSession {
    var currentUser: User? = null
        private set

    fun setUser(user: User) {
        currentUser = user
    }

    fun clear() {
        currentUser = null
    }
}
