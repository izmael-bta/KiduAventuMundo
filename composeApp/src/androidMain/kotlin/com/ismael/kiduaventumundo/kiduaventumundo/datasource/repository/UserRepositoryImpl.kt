package com.ismael.kiduaventumundo.kiduaventumundo.datasource.repository

import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.api.UserApi
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.LoginResponse
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.User
import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.UserRepository

class UserRepositoryImpl(
    private val api: UserApi
) : UserRepository {

    override suspend fun register(user: User): User? =
        api.createUser(user)

    override suspend fun getUserById(userId: Long): User? =
        api.getUserById(userId)

    override suspend fun getUserByNickname(nickname: String): User? =
        api.getUserByNickname(nickname)

    override suspend fun login(nickname: String, passwordHash: String): LoginResponse? =
        api.login(nickname, passwordHash)

    override fun getLastErrorMessage(): String? =
        api.getLastErrorMessage()

    override suspend fun nicknameExists(nickname: String): Boolean =
        api.getUserByNickname(nickname) != null

    override suspend fun updateAvatar(userId: Long, avatarId: String): Boolean =
        api.updateUserAvatar(userId, avatarId)

    override suspend fun updatePassword(userId: Long, passwordHash: String): Boolean =
        api.updateUserPassword(userId, passwordHash)
}