package com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.datasource.repository

import com.ismael.kiduaventumundo.kiduaventumundo.back.db.AppDatabaseHelper
import com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.domain.model.User
import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.userRepository

class UserRepositoryImpl(
    private val db: AppDatabaseHelper
) : userRepository{

    override fun register(user: User): Long {
        return db.registerUser(user)
    }

    override fun getUserById(id: Long): User? {
        return db.getUserById(id)
    }
    override fun getUserByNickname(nickname: String): User? {
        return db.getUserByNickname(nickname)
    }


    override fun nicknameExists(nickname: String): Boolean {
        return db.nicknameExists(nickname)
    }
    override fun updateUser(user: User) {
        db.updateUser(user)
    }


    override fun setSession(userId: Long) {
        db.setSession(userId)
    }

    override fun getSessionUserId(): Long? {
        return db.getSessionUserId()
    }

    override fun clearSession() {
        db.clearSession()
    }
}
