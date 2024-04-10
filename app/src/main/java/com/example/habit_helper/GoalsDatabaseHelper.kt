package com.example.habit_helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.FileOutputStream

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
        if (!checkDatabaseExists(context)) {
            copyDatabase(context)
        }
    }

    private fun checkDatabaseExists(context: Context): Boolean {
        val dbFile = context.getDatabasePath(DATABASE_NAME)
        return dbFile.exists()
    }

    private fun copyDatabase(context: Context) {
        try {
            context.assets.open(DATABASE_NAME).use { inputStream ->
                FileOutputStream(context.getDatabasePath(DATABASE_NAME)).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            Log.d("GoalsDatabaseHelper", "Database copied successfully")
        } catch (e: Exception) {
            Log.e("GoalsDatabaseHelper", "Error copying database: ${e.message}")
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
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

    fun addGoal(goalName: String, goalDescription: String, goalStatus: String) {
        // Check if the goal's name and description are not null or empty
        if (goalName.isEmpty() || goalDescription.isEmpty()) {
            Log.e("GoalsDatabaseHelper", "Goal name or description is null or empty")
            return // Return without inserting if input is invalid
        }

        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_GOAL_NAME, goalName)
            put(COLUMN_GOAL_DESCRIPTION, goalDescription)
            put(COLUMN_GOAL_STATUS, goalStatus)
        }

        db.insert(TABLE_GOALS, null, values)
        db.close()
    }


    fun getAllGoals(): List<Goal> {
        val goalsList = mutableListOf<Goal>()
        val selectQuery = "SELECT * FROM $TABLE_GOALS"
        val db = readableDatabase
        var innerCursor: Cursor? = null

        try {
            innerCursor = db.rawQuery(selectQuery, null)

            innerCursor?.use { cursor ->
                val goalNameIndex = cursor.getColumnIndex(COLUMN_GOAL_NAME)
                val goalDescriptionIndex = cursor.getColumnIndex(COLUMN_GOAL_DESCRIPTION)
                val goalStatusIndex = cursor.getColumnIndex(COLUMN_GOAL_STATUS)

                Log.d("GoalsDatabaseHelper", "goalNameIndex: $goalNameIndex")
                Log.d("GoalsDatabaseHelper", "goalDescriptionIndex: $goalDescriptionIndex")
                Log.d("GoalsDatabaseHelper", "goalStatusIndex: $goalStatusIndex")

                while (cursor.moveToNext()) {
                    val goalName = cursor.getString(goalNameIndex)
                    val goalDescription = cursor.getString(goalDescriptionIndex)
                    val goalStatus = cursor.getString(goalStatusIndex)

                    Log.d("GoalsDatabaseHelper", "Retrieved goalName: $goalName, goalDescription: $goalDescription, goalStatus: $goalStatus")


                    if (goalName != null && goalDescription != null && goalStatus != null) {
                        val goal = Goal(goalName, goalDescription, goalStatus)
                        goalsList.add(goal)
                    } else {
                        Log.e("GoalsDatabaseHelper", "One or more columns were null")
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("GoalsDatabaseHelper", "Error fetching goals", e)
        } finally {
            innerCursor?.close()
            db.close()
        }

        Log.d("GoalsDatabaseHelper", "Retrieved goals from database: $goalsList")
        return goalsList
    }
}