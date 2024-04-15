package com.example.habit_helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "Habit_Helpers.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_USERS = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USERNAME = "Username"
        private const val COLUMN_EMAIL = "Email"
        private const val COLUMN_PASSWORD = "Password"
        private const val COLUMN_RESET_TOKEN = "ResetToken" // New column for storing reset token
        private const val COLUMN_WEIGHT = "Weight" // Column for storing weight
        private const val COLUMN_HEIGHT = "Height" // Column for storing height
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_USERS ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_USERNAME TEXT UNIQUE, $COLUMN_EMAIL TEXT UNIQUE, $COLUMN_PASSWORD TEXT, $COLUMN_RESET_TOKEN TEXT, $COLUMN_WEIGHT REAL, $COLUMN_HEIGHT REAL)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            // Perform migration from old version to version 2
            db.execSQL("ALTER TABLE $TABLE_USERS ADD COLUMN $COLUMN_RESET_TOKEN TEXT")
            db.execSQL("ALTER TABLE $TABLE_USERS ADD COLUMN $COLUMN_WEIGHT REAL")
            db.execSQL("ALTER TABLE $TABLE_USERS ADD COLUMN $COLUMN_HEIGHT REAL")
        }
        // Handle other version upgrades if necessary
    }

    fun addUser(username: String, email: String, password: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_EMAIL, email)
            put(COLUMN_PASSWORD, password)
        }
        val result = db.insert(TABLE_USERS, null, contentValues)
        db.close()
        return result
    }

    fun checkUser(email: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_EMAIL = ?"
        val cursor = db.rawQuery(query, arrayOf(email))
        val count = cursor.count
        cursor.close()
        return count > 0
    }

    fun storeResetToken(email: String, token: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_EMAIL, email)
            put(COLUMN_RESET_TOKEN, token)
        }
        val result = db.insert(TABLE_USERS, null, contentValues)
        db.close()
        return result
    }

    fun getResetToken(email: String): String? {
        val db = this.readableDatabase
        val query = "SELECT $COLUMN_RESET_TOKEN FROM $TABLE_USERS WHERE $COLUMN_EMAIL = ?"
        var token: String? = null
        val cursor = db.rawQuery(query, arrayOf(email))
        cursor.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndex(COLUMN_RESET_TOKEN)
                if (columnIndex != -1) {
                    token = it.getString(columnIndex)
                }
            }
        }
        cursor.close()
        db.close()
        return token
    }

    fun updatePassword(email: String, newPassword: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_PASSWORD, newPassword)
        }
        val result = db.update(TABLE_USERS, contentValues, "$COLUMN_EMAIL = ?", arrayOf(email))
        db.close()
        return result > 0
    }


    fun getUserData(email: String): UserData? {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_EMAIL = ?"
        val cursor = db.rawQuery(query, arrayOf(email))
        var userData: UserData? = null
        cursor.use {
            if (it.moveToFirst()) {
                val usernameIndex = it.getColumnIndex(COLUMN_USERNAME)
                val emailIndex = it.getColumnIndex(COLUMN_EMAIL)
                val passwordIndex = it.getColumnIndex(COLUMN_PASSWORD)
                val weightIndex = it.getColumnIndex(COLUMN_WEIGHT)
                val heightIndex = it.getColumnIndex(COLUMN_HEIGHT)

                if (usernameIndex >= 0 && emailIndex >= 0 && passwordIndex >= 0 && weightIndex >= 0 && heightIndex >= 0) {
                    val username = it.getString(usernameIndex)
                    val userEmail = it.getString(emailIndex)
                    val password = it.getString(passwordIndex)
                    val weight = it.getDouble(weightIndex)
                    val height = it.getDouble(heightIndex)
                    userData = UserData(username, userEmail, password, weight, height)
                }
            }
        }
        cursor.close()
        db.close()
        return userData
    }


    fun updateUserProfileData(email: String, username: String, password: String, weight: Double, height: Double): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_EMAIL, email)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_WEIGHT, weight)
            put(COLUMN_HEIGHT, height)
        }
        val result = db.update(TABLE_USERS, contentValues, "$COLUMN_EMAIL = ?", arrayOf(email))
        db.close()
        return result > 0
    }

}
