package com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id")
    val id: Long = 0L,
    @SerialName("name")
    val name: String,
    @SerialName("age")
    val age: Int,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("password_hash")
    val passwordHash: String = "",
    @SerialName("avatar_id")
    val avatarId: String,
    @SerialName("security_question")
    val securityQuestion: String,
    @SerialName("security_answer_hash")
    val securityAnswerHash: String,
    @SerialName("stars")
    val stars: Int = 0,
    @SerialName("is_active")
    val isActive: Boolean = true,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null
)

@Serializable
data class EnglishLevel(
    @SerialName("level")
    val level: Int,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("pass_stars")
    val passStars: Int,
    @SerialName("display_order")
    val displayOrder: Int,
    @SerialName("is_active")
    val isActive: Boolean
)

@Serializable
data class EnglishActivity(
    @SerialName("id")
    val id: Long = 0L,
    @SerialName("level")
    val level: Int,
    @SerialName("activity_index")
    val activityIndex: Int,
    @SerialName("activity_key")
    val activityKey: String,
    @SerialName("title")
    val title: String,
    @SerialName("is_active")
    val isActive: Boolean
)

@Serializable
data class UserLevelProgress(
    @SerialName("user_id")
    val userId: Long,
    @SerialName("level")
    val level: Int,
    @SerialName("is_unlocked")
    val isUnlocked: Boolean,
    @SerialName("is_completed")
    val isCompleted: Boolean,
    @SerialName("best_stars")
    val bestStars: Int,
    @SerialName("first_completed_at")
    val firstCompletedAt: String? = null,
    @SerialName("last_played_at")
    val lastPlayedAt: String? = null
)

@Serializable
data class UserActivityProgress(
    @SerialName("user_id")
    val userId: Long,
    @SerialName("level")
    val level: Int,
    @SerialName("activity_index")
    val activityIndex: Int,
    @SerialName("stars")
    val stars: Int,
    @SerialName("attempts")
    val attempts: Int,
    @SerialName("successes")
    val successes: Int,
    @SerialName("last_result")
    val lastResult: Boolean? = null,
    @SerialName("last_played_at")
    val lastPlayedAt: String? = null
)

@Serializable
data class ProgressEvent(
    @SerialName("id")
    val id: Long = 0L,
    @SerialName("user_id")
    val userId: Long,
    @SerialName("level")
    val level: Int,
    @SerialName("activity_index")
    val activityIndex: Int? = null,
    @SerialName("event_type")
    val eventType: String,
    @SerialName("stars_delta")
    val starsDelta: Int = 0,
    @SerialName("payload_json")
    val payloadJson: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null
)

@Serializable
data class UserProgressSummary(
    @SerialName("user_id")
    val userId: Long,
    @SerialName("total_stars")
    val totalStars: Int,
    @SerialName("activities_completed")
    val activitiesCompleted: Int,
    @SerialName("current_level")
    val currentLevel: Int,
    @SerialName("levels_unlocked")
    val levelsUnlocked: Int,
    @SerialName("updated_at")
    val updatedAt: String? = null
)

@Serializable
data class SessionRow(
    @SerialName("id")
    val id: Int = 1,
    @SerialName("user_id")
    val userId: Long? = null
)

@Serializable
data class LoginRequest(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("password_hash")
    val passwordHash: String
)

@Serializable
data class LoginResponse(
    @SerialName("success")
    val success: Boolean,
    @SerialName("user")
    val user: User? = null,
    @SerialName("message")
    val message: String? = null
)

@Serializable
data class UpdateAvatarRequest(
    @SerialName("avatar_id")
    val avatarId: String
)

@Serializable
data class UpdatePasswordRequest(
    @SerialName("password_hash")
    val passwordHash: String
)
