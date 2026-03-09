package com.ismael.kiduaventumundo.kiduaventumundo.datasource.repository

import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.api.UserApi
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.LoginResponse
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.User
import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userApi: UserApi
) : UserRepository {

    override suspend fun register(user: User): User? = userApi.createUser(user)

    override suspend fun getUserById(userId: Long): User? = userApi.getUserById(userId)

    override suspend fun getUserByNickname(nickname: String): User? = userApi.getUserByNickname(nickname)

    override suspend fun login(nickname: String, passwordHash: String): LoginResponse? {
        return userApi.login(nickname = nickname, passwordHash = passwordHash)
    }

    override fun getLastErrorMessage(): String? = userApi.getLastErrorMessage()

    override suspend fun nicknameExists(nickname: String): Boolean {
        return userApi.getUserByNickname(nickname) != null
    }

    override suspend fun updateAvatar(userId: Long, avatarId: String): Boolean {
        return userApi.updateUserAvatar(userId = userId, avatarId = avatarId)
    }

    override suspend fun updatePassword(userId: Long, passwordHash: String): Boolean {
        return userApi.updateUserPassword(userId = userId, passwordHash = passwordHash)
    }
}
