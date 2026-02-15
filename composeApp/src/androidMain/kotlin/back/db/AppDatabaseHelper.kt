package com.ismael.kiduaventumundo.kiduaventumundo.back.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.domain.model.User

class AppDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE_USERS (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NAME TEXT NOT NULL,
                $COL_AGE INTEGER NOT NULL,
                $COL_NICKNAME TEXT NOT NULL UNIQUE,
                $COL_AVATAR_ID TEXT NOT NULL,
                $COL_STARS INTEGER NOT NULL DEFAULT 0
            )
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE TABLE $TABLE_SESSION (
                $COL_SESSION_ID INTEGER PRIMARY KEY,
                $COL_SESSION_USER_ID INTEGER,
                FOREIGN KEY($COL_SESSION_USER_ID) REFERENCES $TABLE_USERS($COL_ID)
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SESSION")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
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
            put(COL_AVATAR_ID, user.avatarId)
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
            SELECT $COL_ID, $COL_NAME, $COL_AGE, $COL_NICKNAME, $COL_AVATAR_ID, $COL_STARS
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
                avatarId = it.getString(4),
                stars = it.getInt(5)
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
            put(COL_AVATAR_ID, user.avatarId)
            put(COL_STARS, user.stars)
        }

        return db.update(
            TABLE_USERS,
            values,
            "$COL_ID = ?",
            arrayOf(user.id.toString())
        ) > 0
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
        private const val DATABASE_VERSION = 2 // sube versión por cambio de esquema

        private const val TABLE_USERS = "users"
        private const val COL_ID = "id"
        private const val COL_NAME = "name"
        private const val COL_AGE = "age"
        private const val COL_NICKNAME = "nickname"
        private const val COL_AVATAR_ID = "avatar_id"
        private const val COL_STARS = "stars"

        private const val TABLE_SESSION = "session"
        private const val COL_SESSION_ID = "id"
        private const val COL_SESSION_USER_ID = "user_id"
    }
}
