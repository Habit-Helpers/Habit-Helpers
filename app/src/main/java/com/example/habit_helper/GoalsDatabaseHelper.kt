package com.example.habit_helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.FileOutputStream
import java.io.IOException

class GoalsDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "goals_database.db"
        private const val TABLE_GOALS = "goals"
        private const val COLUMN_ID = "id"
        private const val COLUMN_GOAL_NAME = "goal_name"
        private const val COLUMN_GOAL_DESCRIPTION = "goal_description"
        private const val COLUMN_GOAL_STATUS = "goal_status"
    }

    init {
        if (!context.getDatabasePath(DATABASE_NAME).exists()) {
            copyDatabase(context)
        }
    }

    private fun copyDatabase(context: Context) {
        val assetManager = context.assets
        val dbName = "goals_database.db"
        val dbPath = context.getDatabasePath(dbName).path

        try {
            val inputStream = assetManager.open(dbName)
            val outputStream = FileOutputStream(dbPath)
            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }
            outputStream.flush()
            outputStream.close()
            inputStream.close()

            Log.d("DatabaseCopy", "Database copied successfully to: $dbPath")
        } catch (e: IOException) {
            Log.e("DatabaseCopy", "Error copying database: ${e.message}")
        }
    }

    fun addGoal(goalName: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_GOAL_NAME, goalName)
        }
        db.insert(TABLE_GOALS, null, values)
        db.close()
    }

    fun getAllGoals(): List<Goal> {
        val goalsList = ArrayList<Goal>()
        val selectQuery = "SELECT * FROM $TABLE_GOALS"
        val db = readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
            cursor?.use {
                while (it.moveToNext()) {
                    val goalNameIndex = it.getColumnIndex(COLUMN_GOAL_NAME)
                    val goalDescriptionIndex = it.getColumnIndex(COLUMN_GOAL_DESCRIPTION)
                    val goalStatusIndex = it.getColumnIndex(COLUMN_GOAL_STATUS)

                    // Check if column indices are valid
                    if (goalNameIndex != -1 && goalDescriptionIndex != -1 && goalStatusIndex != -1) {
                        val goalName = it.getString(goalNameIndex)
                        val goalDescription = it.getString(goalDescriptionIndex)
                        val goalStatus = it.getString(goalStatusIndex)
                        val goal = Goal(goalName, goalDescription, goalStatus)
                        goalsList.add(goal)
                    } else {
                        Log.e("GoalsDatabaseHelper", "Invalid column index")
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("GoalsDatabaseHelper", "Error fetching goals: ${e.message}")
        } finally {
            cursor?.close()
            db.close()
        }

        Log.d("GoalsDatabaseHelper", "Retrieved goals from database: $goalsList")

        return goalsList
    }

    override fun onCreate(db: SQLiteDatabase) {
        Log.d("GoalsDatabaseHelper", "onCreate() method called")

        val createTableQuery = ("CREATE TABLE $TABLE_GOALS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY,"
                + "$COLUMN_GOAL_NAME TEXT,"
                + "$COLUMN_GOAL_DESCRIPTION TEXT,"
                + "$COLUMN_GOAL_STATUS TEXT"
                + ")")
        try {
            db.execSQL(createTableQuery)
            Log.d("GoalsDatabaseHelper", "Table created successfully")
        } catch (e: SQLException) {
            Log.e("GoalsDatabaseHelper", "Error creating table: ${e.message}")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_GOALS")
        onCreate(db)
    }
}



