package com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english

import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.EnglishManager

enum class ActivityStatus {
    COMPLETED,
    AVAILABLE,
    BLOCKED
}

data class ActivityProgress(
    val activityIndex: Int,
    val status: ActivityStatus,
    val earnedStars: Int?
)

object EnglishActivitiesUseCase {
    fun getActivities(level: Int, totalActivities: Int): List<ActivityProgress> {
        val starsByActivity = EnglishManager.getActivityStars(level, totalActivities)
        return (0 until totalActivities).map { activityIndex ->
            val earned = starsByActivity[activityIndex]
            val unlocked = activityIndex == 0 || starsByActivity[activityIndex - 1] != null
            val status = when {
                earned != null -> ActivityStatus.COMPLETED
                unlocked -> ActivityStatus.AVAILABLE
                else -> ActivityStatus.BLOCKED
            }
            ActivityProgress(
                activityIndex = activityIndex,
                status = status,
                earnedStars = earned
            )
        }
    }
}
