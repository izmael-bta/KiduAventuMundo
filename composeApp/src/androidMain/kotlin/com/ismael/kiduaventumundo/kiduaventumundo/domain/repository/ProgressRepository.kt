package com.ismael.kiduaventumundo.kiduaventumundo.domain.repository

import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.UserActivityProgress
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.UserLevelProgress

interface ProgressRepository {
    suspend fun getLevelProgress(userId: Long): List<UserLevelProgress>
    suspend fun getLevelProgress(userId: Long, level: Int): UserLevelProgress?
    suspend fun upsertLevelProgress(userId: Long, level: Int, progress: UserLevelProgress): Boolean
    suspend fun getActivityProgress(userId: Long, level: Int): List<UserActivityProgress>
    suspend fun upsertActivityProgress(
        userId: Long,
        level: Int,
        activityIndex: Int,
        progress: UserActivityProgress
    ): Boolean
}
