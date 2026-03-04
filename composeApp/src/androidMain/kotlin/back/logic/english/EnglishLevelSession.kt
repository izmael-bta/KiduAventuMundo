package com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english

import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.EnglishManager

/**
 * Estado de ejecucion de un nivel durante una sesion de juego.
 */
data class LevelSessionState(
    val index: Int,
    val starsLevel: Int,
    val mistakes: Int,
    val feedback: String?,
    val locked: Boolean,
    val showEndDialog: Boolean,
    val passed: Boolean,
    val totalActivities: Int,
    val passStars: Int
)

/**
 * Resultado de seleccionar una opcion.
 */
data class SelectionResult(
    val state: LevelSessionState,
    val isCorrect: Boolean
)

/**
 * Accion final tras confirmar dialogo de cierre de nivel.
 */
enum class DialogConfirmAction {
    CONTINUE,
    RETRY
}

/**
 * Orquesta la logica de juego de un nivel:
 * intentos, estrellas, avance, finalizacion y desbloqueo.
 */
class EnglishLevelSession(
    private val level: Int,
    private val totalActivities: Int,
    private val passStars: Int = 13
) {
    private var nextLevelAfterCompletion: Int? = null
    private val activityStars = EnglishManager
        .getActivityStars(level = level, totalActivities = totalActivities)
        .toMutableList()

    private var _state = LevelSessionState(
        index = EnglishManager.consumeStartActivity(level).coerceIn(0, totalActivities - 1),
        starsLevel = activityStars.sumOf { it ?: 0 },
        mistakes = 0,
        feedback = null,
        locked = false,
        showEndDialog = false,
        passed = false,
        totalActivities = totalActivities,
        passStars = passStars
    )
    val state: LevelSessionState get() = _state

    /**
     * Procesa la opcion seleccionada y actualiza estrellas/feedback.
     */
    fun submitSelection(selectedId: String, correctId: String): SelectionResult {
        if (_state.locked || _state.showEndDialog) {
            return SelectionResult(_state, isCorrect = false)
        }

        val isCorrect = selectedId == correctId
        if (!isCorrect) {
            _state = _state.copy(
                mistakes = _state.mistakes + 1,
                feedback = "Intenta otra vez"
            )
            return SelectionResult(_state, isCorrect = false)
        }

        val earned = earnedStarsForThisActivity(_state.mistakes)
        EnglishManager.recordActivityResult(
            level = level,
            activityIndex = _state.index,
            starsEarned = earned,
            totalActivities = totalActivities
        )
        activityStars[_state.index] = maxOf(activityStars[_state.index] ?: -1, earned)

        _state = _state.copy(
            locked = true,
            starsLevel = activityStars.sumOf { it ?: 0 },
            feedback = "+$earned *"
        )
        return SelectionResult(_state, isCorrect = true)
    }

    /**
     * Avanza a la siguiente actividad o abre dialogo final si ya termino.
     */
    fun advanceAfterCorrect(): LevelSessionState {
        if (!_state.locked) return _state

        val isLast = _state.index == totalActivities - 1
        _state = if (isLast) {
            _state.copy(
                passed = _state.starsLevel >= passStars,
                showEndDialog = true
            )
        } else {
            _state.copy(
                index = _state.index + 1,
                mistakes = 0,
                feedback = null,
                locked = false
            )
        }
        return _state
    }

    /**
     * Reinicia la sesion de nivel conservando progreso previo ya guardado.
     */
    fun restartLevel(): LevelSessionState {
        _state = _state.copy(
            index = 0,
            starsLevel = activityStars.sumOf { it ?: 0 },
            mistakes = 0,
            feedback = null,
            locked = false,
            showEndDialog = false,
            passed = false
        )
        return _state
    }

    fun closeDialog(): LevelSessionState {
        _state = _state.copy(showEndDialog = false)
        return _state
    }

    /**
     * Confirma resultado final:
     * - si aprobo, marca nivel completado y desbloquea siguiente
     * - si no aprobo, reinicia sesion de nivel
     */
    fun confirmDialog(): DialogConfirmAction {
        return if (_state.passed) {
            nextLevelAfterCompletion = EnglishManager.completeLevelAndGetNext(
                level = level,
                starsEarned = _state.starsLevel
            )
            _state = _state.copy(showEndDialog = false)
            DialogConfirmAction.CONTINUE
        } else {
            restartLevel()
            DialogConfirmAction.RETRY
        }
    }

    /**
     * Devuelve el siguiente nivel calculado al confirmar nivel aprobado.
     * Se consume una sola vez para evitar reuso accidental.
     */
    fun consumeNextLevelAfterCompletion(): Int? {
        val nextLevel = nextLevelAfterCompletion
        nextLevelAfterCompletion = null
        return nextLevel
    }

    private fun earnedStarsForThisActivity(mistakes: Int): Int = when {
        mistakes == 0 -> 3
        mistakes == 1 -> 2
        mistakes == 2 -> 1
        else -> 0
    }
}
