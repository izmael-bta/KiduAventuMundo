package com.ismael.kiduaventumundo.kiduaventumundo.back.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.domain.model.User

/**
 * Helper de SQLite local.
 *
 * Mantiene:
 * - usuarios y sesion
 * - progreso de niveles y actividades de Ingles
 */
class AppDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        createBaseTables(db)
        createEnglishProgressTables(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Migracion incremental: agrega tablas de progreso desde version 5.
        if (oldVersion < 5) {
            createBaseTables(db)
            createEnglishProgressTables(db)
        }
    }

    // -------------------------
    // USERS
    // -------------------------

    fun nicknameExists(nickname: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT $COL_ID FROM $TABLE_USERS WHERE $COL_NICKNAME = ? LIMIT 1",
            arrayOf(nickname.trim())
        )
        cursor.use { return it.moveToFirst() }
    }

    fun registerUser(user: User): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NAME, user.name.trim())
            put(COL_AGE, user.age)
            put(COL_NICKNAME, user.nickname.trim())
            put(COL_PASSWORD_HASH, user.passwordHash)
            put(COL_AVATAR_ID, user.avatarId)
            put(COL_SECURITY_QUESTION, user.securityQuestion.trim())
            put(COL_SECURITY_ANSWER_HASH, user.securityAnswerHash)
            put(COL_STARS, user.stars)
        }
        return db.insert(TABLE_USERS, null, values)
    }

    fun getUserIdByNickname(nickname: String): Long? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT $COL_ID FROM $TABLE_USERS WHERE $COL_NICKNAME = ? LIMIT 1",
            arrayOf(nickname.trim())
        )
        cursor.use { return if (it.moveToFirst()) it.getLong(0) else null }
    }

    fun getUserByNickname(nickname: String): User? {
        val userId = getUserIdByNickname(nickname) ?: return null
        return getUserById(userId)
    }

    fun getUserById(userId: Long): User? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            """
            SELECT $COL_ID, $COL_NAME, $COL_AGE, $COL_NICKNAME, $COL_PASSWORD_HASH, $COL_AVATAR_ID, $COL_SECURITY_QUESTION, $COL_SECURITY_ANSWER_HASH, $COL_STARS
            FROM $TABLE_USERS
            WHERE $COL_ID = ?
            LIMIT 1
            """.trimIndent(),
            arrayOf(userId.toString())
        )

        cursor.use {
            if (!it.moveToFirst()) return null
            return User(
                id = it.getLong(0),
                name = it.getString(1),
                age = it.getInt(2),
                nickname = it.getString(3),
                passwordHash = it.getString(4),
                avatarId = it.getString(5),
                securityQuestion = it.getString(6),
                securityAnswerHash = it.getString(7),
                stars = it.getInt(8)
            )
        }
    }

    fun updateUserAvatar(userId: Long, avatarId: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_AVATAR_ID, avatarId)
        }
        return db.update(
            TABLE_USERS,
            values,
            "$COL_ID = ?",
            arrayOf(userId.toString())
        ) > 0
    }

    fun updateUser(user: User): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NAME, user.name.trim())
            put(COL_AGE, user.age)
            put(COL_NICKNAME, user.nickname.trim())
            put(COL_PASSWORD_HASH, user.passwordHash)
            put(COL_AVATAR_ID, user.avatarId)
            put(COL_SECURITY_QUESTION, user.securityQuestion.trim())
            put(COL_SECURITY_ANSWER_HASH, user.securityAnswerHash)
            put(COL_STARS, user.stars)
        }

        return db.update(
            TABLE_USERS,
            values,
            "$COL_ID = ?",
            arrayOf(user.id.toString())
        ) > 0
    }

    fun updateUserPassword(userId: Long, passwordHash: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_PASSWORD_HASH, passwordHash)
        }
        return db.update(
            TABLE_USERS,
            values,
            "$COL_ID = ?",
            arrayOf(userId.toString())
        ) > 0
    }

    fun updateUserStars(userId: Long, stars: Int): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_STARS, stars.coerceAtLeast(0))
        }
        return db.update(
            TABLE_USERS,
            values,
            "$COL_ID = ?",
            arrayOf(userId.toString())
        ) > 0
    }

    // -------------------------
    // ENGLISH PROGRESS
    // -------------------------

    data class LevelProgressRow(
        val level: Int,
        val isUnlocked: Boolean,
        val isCompleted: Boolean
    )

    fun getEnglishLevelProgress(userId: Long): List<LevelProgressRow> {
        val db = readableDatabase
        val cursor = db.rawQuery(
            """
            SELECT $COL_PROGRESS_LEVEL, $COL_PROGRESS_IS_UNLOCKED, $COL_PROGRESS_IS_COMPLETED
            FROM $TABLE_ENGLISH_LEVEL_PROGRESS
            WHERE $COL_PROGRESS_USER_ID = ?
            ORDER BY $COL_PROGRESS_LEVEL ASC
            """.trimIndent(),
            arrayOf(userId.toString())
        )
        cursor.use {
            val rows = mutableListOf<LevelProgressRow>()
            while (it.moveToNext()) {
                rows.add(
                    LevelProgressRow(
                        level = it.getInt(0),
                        isUnlocked = it.getInt(1) == 1,
                        isCompleted = it.getInt(2) == 1
                    )
                )
            }
            return rows
        }
    }

    fun upsertEnglishLevelProgress(
        userId: Long,
        level: Int,
        isUnlocked: Boolean,
        isCompleted: Boolean
    ) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_PROGRESS_USER_ID, userId)
            put(COL_PROGRESS_LEVEL, level)
            put(COL_PROGRESS_IS_UNLOCKED, if (isUnlocked) 1 else 0)
            put(COL_PROGRESS_IS_COMPLETED, if (isCompleted) 1 else 0)
        }
        db.insertWithOnConflict(
            TABLE_ENGLISH_LEVEL_PROGRESS,
            null,
            values,
            SQLiteDatabase.CONFLICT_REPLACE
        )
    }

    fun getEnglishActivityStars(userId: Long, level: Int, totalActivities: Int): List<Int?> {
        val result = MutableList(totalActivities) { null as Int? }
        val db = readableDatabase
        val cursor = db.rawQuery(
            """
            SELECT $COL_ACTIVITY_INDEX, $COL_ACTIVITY_STARS
            FROM $TABLE_ENGLISH_ACTIVITY_PROGRESS
            WHERE $COL_ACTIVITY_USER_ID = ? AND $COL_ACTIVITY_LEVEL = ?
            ORDER BY $COL_ACTIVITY_INDEX ASC
            """.trimIndent(),
            arrayOf(userId.toString(), level.toString())
        )
        cursor.use {
            while (it.moveToNext()) {
                val index = it.getInt(0)
                val stars = it.getInt(1)
                if (index in result.indices) {
                    result[index] = stars
                }
            }
        }
        return result
    }

    fun replaceEnglishActivityStars(userId: Long, level: Int, starsByActivity: List<Int?>) {
        val db = writableDatabase
        db.beginTransaction()
        try {
            // Reemplazo total por nivel para mantener consistencia del snapshot.
            db.delete(
                TABLE_ENGLISH_ACTIVITY_PROGRESS,
                "$COL_ACTIVITY_USER_ID = ? AND $COL_ACTIVITY_LEVEL = ?",
                arrayOf(userId.toString(), level.toString())
            )
            starsByActivity.forEachIndexed { index, stars ->
                if (stars != null) {
                    val values = ContentValues().apply {
                        put(COL_ACTIVITY_USER_ID, userId)
                        put(COL_ACTIVITY_LEVEL, level)
                        put(COL_ACTIVITY_INDEX, index)
                        put(COL_ACTIVITY_STARS, stars.coerceIn(0, 3))
                    }
                    db.insert(TABLE_ENGLISH_ACTIVITY_PROGRESS, null, values)
                }
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    // -------------------------
    // SESSION
    // -------------------------

    fun setSession(userId: Long) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_SESSION_ID, 1)
            put(COL_SESSION_USER_ID, userId)
        }
        db.insertWithOnConflict(TABLE_SESSION, null, values, SQLiteDatabase.CONFLICT_REPLACE)
    }

    fun getSessionUserId(): Long? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT $COL_SESSION_USER_ID FROM $TABLE_SESSION WHERE $COL_SESSION_ID = 1 LIMIT 1",
            null
        )
        cursor.use { return if (it.moveToFirst() && !it.isNull(0)) it.getLong(0) else null }
    }

    fun clearSession() {
        val db = writableDatabase
        db.delete(TABLE_SESSION, "$COL_SESSION_ID = 1", null)
    }

    companion object {
        private const val DATABASE_NAME = "kidu_aventumundo.db"
        private const val DATABASE_VERSION = 5

        private const val TABLE_USERS = "users"
        private const val COL_ID = "id"
        private const val COL_NAME = "name"
        private const val COL_AGE = "age"
        private const val COL_NICKNAME = "nickname"
        private const val COL_PASSWORD_HASH = "password_hash"
        private const val COL_AVATAR_ID = "avatar_id"
        private const val COL_SECURITY_QUESTION = "security_question"
        private const val COL_SECURITY_ANSWER_HASH = "security_answer_hash"
        private const val COL_STARS = "stars"

        private const val TABLE_SESSION = "session"
        private const val COL_SESSION_ID = "id"
        private const val COL_SESSION_USER_ID = "user_id"

        private const val TABLE_ENGLISH_LEVEL_PROGRESS = "english_level_progress"
        private const val COL_PROGRESS_USER_ID = "user_id"
        private const val COL_PROGRESS_LEVEL = "level"
        private const val COL_PROGRESS_IS_UNLOCKED = "is_unlocked"
        private const val COL_PROGRESS_IS_COMPLETED = "is_completed"

        private const val TABLE_ENGLISH_ACTIVITY_PROGRESS = "english_activity_progress"
        private const val COL_ACTIVITY_USER_ID = "user_id"
        private const val COL_ACTIVITY_LEVEL = "level"
        private const val COL_ACTIVITY_INDEX = "activity_index"
        private const val COL_ACTIVITY_STARS = "stars"
    }

    private fun createBaseTables(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS $TABLE_USERS (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NAME TEXT NOT NULL,
                $COL_AGE INTEGER NOT NULL,
                $COL_NICKNAME TEXT NOT NULL UNIQUE,
                $COL_PASSWORD_HASH TEXT NOT NULL,
                $COL_AVATAR_ID TEXT NOT NULL,
                $COL_SECURITY_QUESTION TEXT NOT NULL,
                $COL_SECURITY_ANSWER_HASH TEXT NOT NULL,
                $COL_STARS INTEGER NOT NULL DEFAULT 0
            )
            """.trimIndent()
        )
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS $TABLE_SESSION (
                $COL_SESSION_ID INTEGER PRIMARY KEY,
                $COL_SESSION_USER_ID INTEGER,
                FOREIGN KEY($COL_SESSION_USER_ID) REFERENCES $TABLE_USERS($COL_ID)
            )
            """.trimIndent()
        )
    }

    private fun createEnglishProgressTables(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS $TABLE_ENGLISH_LEVEL_PROGRESS (
                $COL_PROGRESS_USER_ID INTEGER NOT NULL,
                $COL_PROGRESS_LEVEL INTEGER NOT NULL,
                $COL_PROGRESS_IS_UNLOCKED INTEGER NOT NULL DEFAULT 0,
                $COL_PROGRESS_IS_COMPLETED INTEGER NOT NULL DEFAULT 0,
                PRIMARY KEY ($COL_PROGRESS_USER_ID, $COL_PROGRESS_LEVEL),
                FOREIGN KEY($COL_PROGRESS_USER_ID) REFERENCES $TABLE_USERS($COL_ID)
            )
            """.trimIndent()
        )
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS $TABLE_ENGLISH_ACTIVITY_PROGRESS (
                $COL_ACTIVITY_USER_ID INTEGER NOT NULL,
                $COL_ACTIVITY_LEVEL INTEGER NOT NULL,
                $COL_ACTIVITY_INDEX INTEGER NOT NULL,
                $COL_ACTIVITY_STARS INTEGER NOT NULL DEFAULT 0,
                PRIMARY KEY ($COL_ACTIVITY_USER_ID, $COL_ACTIVITY_LEVEL, $COL_ACTIVITY_INDEX),
                FOREIGN KEY($COL_ACTIVITY_USER_ID) REFERENCES $TABLE_USERS($COL_ID)
            )
            """.trimIndent()
        )
    }
}

