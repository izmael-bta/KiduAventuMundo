package com.ismael.kiduaventumundo.kiduaventumundo.datasource.repository

import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.api.ProgressApi
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.UserActivityProgress
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.UserLevelProgress
import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.ProgressRepository

class ProgressRepositoryImpl(
    private val progressApi: ProgressApi
) : ProgressRepository {
    override suspend fun getLevelProgress(userId: Long): List<UserLevelProgress> {
        return progressApi.getUserLevelProgress(userId)
    }

    override suspend fun getLevelProgress(userId: Long, level: Int): UserLevelProgress? {
        return progressApi.getUserLevelProgress(userId, level)
    }

    override suspend fun upsertLevelProgress(
        userId: Long,
        level: Int,
        progress: UserLevelProgress
    ): Boolean {
        return progressApi.upsertUserLevelProgress(userId, level, progress)
    }

    override suspend fun getActivityProgress(userId: Long, level: Int): List<UserActivityProgress> {
        return progressApi.getUserActivityProgress(userId, level)
    }

    override suspend fun upsertActivityProgress(
        userId: Long,
        level: Int,
        activityIndex: Int,
        progress: UserActivityProgress
    ): Boolean {
        return progressApi.upsertUserActivityProgress(userId, level, activityIndex, progress)
    }
}
