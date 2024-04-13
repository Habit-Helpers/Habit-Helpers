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

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "tasks_database.db"
        private const val TABLE_TASKS = "tasks"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TASK_NAME = "task_name"
        private const val COLUMN_TASK_DATE = "task_date"
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
            Log.d("TasksDatabaseHelper", "Database copied successfully")
        } catch (e: Exception) {
            Log.e("TasksDatabaseHelper", "Error copying database: ${e.message}")
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = ("CREATE TABLE $TABLE_TASKS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY,"
                + "$COLUMN_TASK_NAME TEXT,"
                + "$COLUMN_TASK_DATE TEXT,"
                + ")")
        try {
            db.execSQL(createTableQuery)
            Log.d("TasksDatabaseHelper", "Table created successfully")
        } catch (e: SQLException) {
            Log.e("TasksDatabaseHelper", "Error creating table: ${e.message}")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TASKS")
        onCreate(db)
    }

    fun addTask(taskName: String, taskDate: String) {
        // Check if the task's name and date are not null or empty
        if (taskName.isEmpty() || taskDate.isEmpty()) {
            Log.e("TasksDatabaseHelper", "Task name or date is null or empty")
            return // Return without inserting if input is invalid
        }

        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TASK_NAME, taskName)
            put(COLUMN_TASK_DATE, taskDate)
        }

        db.insert(TABLE_TASKS, null, values)
        db.close()
    }

    fun getAllTasks(): List<Task> {
        val tasksList = mutableListOf<Task>()
        val selectQuery = "SELECT * FROM $TABLE_TASKS"
        val db = readableDatabase
        var innerCursor: Cursor? = null

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
}
