package com.ismael.kiduaventumundo.kiduaventumundo.back.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ismael.kiduaventumundo.kiduaventumundo.back.model.User

class AppDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // Tabla de usuarios
        db.execSQL(
            """
            CREATE TABLE $TABLE_USERS (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_EMAIL TEXT NOT NULL UNIQUE,
                $COL_NAME TEXT NOT NULL,
                $COL_AGE INTEGER NOT NULL,
                $COL_NICKNAME TEXT NOT NULL UNIQUE,
                $COL_PASSWORD TEXT NOT NULL
            )
            """.trimIndent()
        )

        // Tabla de sesión (solo 1 fila con id=1)
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
            put(COL_EMAIL, user.email.trim())
            put(COL_NAME, user.name.trim())
            put(COL_AGE, user.age)
            put(COL_NICKNAME, user.nickname.trim())
            put(COL_PASSWORD, user.password) // (para integrador está ok; luego podemos hashear)
        }
        return db.insert(TABLE_USERS, null, values)
    }

    /**
     * Devuelve el userId si las credenciales son correctas, o null si no coinciden.
     */
    fun getUserIdByCredentials(nickname: String, password: String): Long? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            """
            SELECT $COL_ID 
            FROM $TABLE_USERS 
            WHERE $COL_NICKNAME = ? AND $COL_PASSWORD = ?
            LIMIT 1
            """.trimIndent(),
            arrayOf(nickname.trim(), password)
        )
        cursor.use {
            return if (it.moveToFirst()) it.getLong(0) else null
        }
    }

    fun getUserById(userId: Long): User? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            """
            SELECT $COL_ID, $COL_EMAIL, $COL_NAME, $COL_AGE, $COL_NICKNAME, $COL_PASSWORD
            FROM $TABLE_USERS
            WHERE $COL_ID = ?
            LIMIT 1
            """.trimIndent(),
            arrayOf(userId.toString())
        )

        cursor.use {
            if (!it.moveToFirst()) return null

            val id = it.getLong(0)
            val email = it.getString(1)
            val name = it.getString(2)
            val age = it.getInt(3)
            val nickname = it.getString(4)
            val password = it.getString(5)

            return User(
                id = id,
                email = email,
                name = name,
                age = age,
                nickname = nickname,
                password = password
            )
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

        // Reemplaza si ya existía (id=1)
        db.insertWithOnConflict(
            TABLE_SESSION,
            null,
            values,
            SQLiteDatabase.CONFLICT_REPLACE
        )
    }

    /**
     * Devuelve el userId logueado si existe sesión, o null si no hay.
     */
    fun getSessionUserId(): Long? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            """
            SELECT $COL_SESSION_USER_ID
            FROM $TABLE_SESSION
            WHERE $COL_SESSION_ID = 1
            LIMIT 1
            """.trimIndent(),
            null
        )

        cursor.use {
            return if (it.moveToFirst() && !it.isNull(0)) it.getLong(0) else null
        }
    }

    fun clearSession() {
        val db = writableDatabase
        db.delete(TABLE_SESSION, "$COL_SESSION_ID = 1", null)
    }

    companion object {
        private const val DATABASE_NAME = "kidu_aventumundo.db"
        private const val DATABASE_VERSION = 2

        // USERS
        private const val TABLE_USERS = "users"
        private const val COL_ID = "id"
        private const val COL_EMAIL = "email"
        private const val COL_NAME = "name"
        private const val COL_AGE = "age"
        private const val COL_NICKNAME = "nickname"
        private const val COL_PASSWORD = "password"

        // SESSION
        private const val TABLE_SESSION = "session"
        private const val COL_SESSION_ID = "id"
        private const val COL_SESSION_USER_ID = "user_id"
    }
}
