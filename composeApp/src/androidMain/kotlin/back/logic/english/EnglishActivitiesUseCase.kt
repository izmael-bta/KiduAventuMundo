package com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english

import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.EnglishManager

/**
 * Estado visual/logico de una actividad dentro del menu de actividades.
 */
enum class ActivityStatus {
    COMPLETED,
    AVAILABLE,
    BLOCKED
}

/**
 * Progreso de una actividad individual para renderizar tarjetas/botones.
 */
data class ActivityProgress(
    val activityIndex: Int,
    val status: ActivityStatus,
    val earnedStars: Int?
)

/**
 * Caso de uso para construir la lista de actividades visibles por nivel.
 */
object EnglishActivitiesUseCase {
    /**
     * Regla de desbloqueo:
     * - actividad 0 siempre disponible
     * - el resto se habilita si la anterior ya fue completada
     */
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
