package com.example.habit_helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.FileOutputStream

class TasksDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Companion object with constant values
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "tasks_database.db"
        private const val TABLE_TASKS = "tasks"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TASK_NAME = "task_name"
        private const val COLUMN_TASK_DATE = "task_date"
        private const val COLUMN_TASK_ADDED = "task_added"
    }

    init {
        // Check if the database exists and if the required table exists
        if (!checkDatabaseExists(context) || !checkTableExists()) {
            copyDatabase(context)
        }
    }

    // Function to check if the database file exists
    private fun checkDatabaseExists(context: Context): Boolean {
        val dbFile = context.getDatabasePath(DATABASE_NAME)
        return dbFile.exists()
    }

    // Function to check if the required table exists
    private fun checkTableExists(): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='$TABLE_TASKS'", null)
        val tableExists = cursor.moveToFirst()
        cursor.close()
        db.close()
        return tableExists
    }

    // Function to copy the database file from assets
    private fun copyDatabase(context: Context) {
        try {
            context.assets.open(DATABASE_NAME).use { inputStream ->
                FileOutputStream(context.getDatabasePath(DATABASE_NAME)).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            Log.d("TasksDatabaseHelper", "Database copied successfully")
        } catch (e: Exception) {
            Log.e("TasksDatabaseHelper", "Error copying database: ${e.message}")
        }
    }

    // onCreate method to create the database table
    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = ("CREATE TABLE $TABLE_TASKS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY,"
                + "$COLUMN_TASK_NAME TEXT,"
                + "$COLUMN_TASK_DATE TEXT,"
                + "$COLUMN_TASK_ADDED INTEGER DEFAULT 0"
                + ")")
        try {
            db.execSQL(createTableQuery)
            Log.d("TasksDatabaseHelper", "Table created successfully")
        } catch (e: SQLException) {
            Log.e("TasksDatabaseHelper", "Error creating table: ${e.message}")
        }
    }

    // onUpgrade method to handle database upgrades
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TASKS")
        onCreate(db)
    }

    // Function to add a task to the database
    fun addTask(taskName: String, taskDate: String) {
        // Check if the task's name and date are not null or empty
        if (taskName.isEmpty() || taskDate.isEmpty()) {
            Log.e("TasksDatabaseHelper", "Task name or date is null or empty")
            return // Return without inserting if input is invalid
        }

        Log.d("TasksDatabaseHelper", "Adding task - Name: $taskName, Date: $taskDate")

        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TASK_NAME, taskName)
            put(COLUMN_TASK_DATE, taskDate)
        }

        // Insert the task into the database
        db.insert(TABLE_TASKS, null, values)

        // Update the flag for the corresponding date to indicate that a task has been added
        val updateValues = ContentValues().apply {
            put(COLUMN_TASK_ADDED, 1) // Set the flag to 1 (true)
        }
        val whereClause = "$COLUMN_TASK_DATE = ?"
        val whereArgs = arrayOf(taskDate)
        db.update(TABLE_TASKS, updateValues, whereClause, whereArgs)

        db.close()
    }

    // Function to update the task added flag for a specific date
    fun updateTaskAddedFlag(date: String) {
        val db = writableDatabase
        val updateValues = ContentValues().apply {
            put(COLUMN_TASK_ADDED, 1) // Set the flag to 1 (true)
        }
        val whereClause = "$COLUMN_TASK_DATE = ?"
        val whereArgs = arrayOf(date)
        db.update(TABLE_TASKS, updateValues, whereClause, whereArgs)
        db.close()
    }

    fun getAllTasks(): List<Task> {
        val tasksList = mutableListOf<Task>()
        val selectQuery = "SELECT * FROM $TABLE_TASKS"
        val db = readableDatabase
        var innerCursor: Cursor? = null // Renamed to avoid shadowing

        try {
            innerCursor = db.rawQuery(selectQuery, null)

            innerCursor?.use { cursor ->
                val taskNameIndex = cursor.getColumnIndex(COLUMN_TASK_NAME)
                val taskDateIndex = cursor.getColumnIndex(COLUMN_TASK_DATE)

                while (cursor.moveToNext()) {
                    val taskName = cursor.getString(taskNameIndex)
                    val taskDate = cursor.getString(taskDateIndex)

                    if (taskName != null && taskDate != null) {
                        val task = Task(taskName, taskDate)
                        tasksList.add(task)
                    } else {
                        Log.e("TasksDatabaseHelper", "One or more columns were null")
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("TasksDatabaseHelper", "Error fetching tasks", e)
        } finally {
            innerCursor?.close()
            db.close()
        }

        Log.d("TasksDatabaseHelper", "Retrieved tasks from database: $tasksList")
        return tasksList
    }

    fun getTasksForDate(selectedDate: String): List<Task> {
        val tasksList = mutableListOf<Task>()
        val selectQuery = "SELECT * FROM $TABLE_TASKS WHERE $COLUMN_TASK_DATE = ?"
        val db = readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, arrayOf(selectedDate))

            cursor?.use { innerCursor ->
                val taskNameIndex = innerCursor.getColumnIndex(COLUMN_TASK_NAME)
                val taskDateIndex = innerCursor.getColumnIndex(COLUMN_TASK_DATE)

                while (innerCursor.moveToNext()) {
                    val taskName = innerCursor.getString(taskNameIndex)
                    val taskDate = innerCursor.getString(taskDateIndex)

                    if (taskName != null && taskDate != null) {
                        val task = Task(taskName, taskDate)
                        tasksList.add(task)
                    } else {
                        Log.e("TasksDatabaseHelper", "One or more columns were null")
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("TasksDatabaseHelper", "Error fetching tasks for date: $selectedDate", e)
        } finally {
            cursor?.close()
            db.close()
        }

        Log.d("TasksDatabaseHelper", "Retrieved tasks for date $selectedDate from database: $tasksList")
        return tasksList
    }

    fun isTableEmpty(): Boolean {
        val db = readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_TASKS", null)
            if (cursor != null && cursor.moveToFirst()) {
                val count = cursor.getInt(0)
                return count == 0
            }
        } catch (e: Exception) {
            Log.e("TasksDatabaseHelper", "Error checking if table is empty", e)
        } finally {
            cursor?.close()
            db.close()
        }

        return true // Assume table is empty in case of an error
    }
}

