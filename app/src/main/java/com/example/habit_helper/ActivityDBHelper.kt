package com.example.habit_helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.FileOutputStream

class ActivityDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "activity_database.db"
        private const val TABLE_ACTIVITIES = "activities"
        private const val COLUMN_ID = "id"
        private const val COLUMN_ACTIVITY_NAME = "activity_name"
        private const val COLUMN_ACTIVITY_DESCRIPTION = "activity_description"
        private const val COLUMN_ACTIVITY_COLOR_LABEL = "activity_color_label"
    }

    init {
        if (!checkDatabaseExists(context) || !checkTableExists()) {
            copyDatabase(context)
        }
    }

    private fun checkDatabaseExists(context: Context): Boolean {
        val dbFile = context.getDatabasePath(DATABASE_NAME)
        return dbFile.exists()
    }

    private fun checkTableExists(): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='$TABLE_ACTIVITIES'", null)
        val tableExists = cursor.moveToFirst()
        cursor.close()
        db.close()
        return tableExists
    }

    private fun copyDatabase(context: Context) {
        try {
            context.assets.open(DATABASE_NAME).use { inputStream ->
                FileOutputStream(context.getDatabasePath(DATABASE_NAME)).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            Log.d("ActivityDBHelper", "Database copied successfully")
        } catch (e: Exception) {
            Log.e("ActivityDBHelper", "Error copying database: ${e.message}")
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = ("CREATE TABLE $TABLE_ACTIVITIES ("
                + "$COLUMN_ID INTEGER PRIMARY KEY,"
                + "$COLUMN_ACTIVITY_NAME TEXT,"
                + "$COLUMN_ACTIVITY_DESCRIPTION TEXT,"
                + "$COLUMN_ACTIVITY_COLOR_LABEL TEXT"
                + ")")
        try {
            db.execSQL(createTableQuery)
            Log.d("ActivityDBHelper", "Table created successfully")
        } catch (e: SQLException) {
            Log.e("ActivityDBHelper", "Error creating table: ${e.message}")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ACTIVITIES")
        onCreate(db)
    }

    fun addActivity(activity: ActivityItem) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ACTIVITY_NAME, activity.name)
            put(COLUMN_ACTIVITY_DESCRIPTION, activity.description)
            put(COLUMN_ACTIVITY_COLOR_LABEL, activity.colorLabel)
        }
        db.insert(TABLE_ACTIVITIES, null, values)
        db.close()
    }

    fun getAllActivities(): List<ActivityItem> {
        val activitiesList = mutableListOf<ActivityItem>()
        val selectQuery = "SELECT * FROM $TABLE_ACTIVITIES"
        val db = readableDatabase
        var innerCursor: Cursor? = null

        try {
            innerCursor = db.rawQuery(selectQuery, null)

            innerCursor?.use { cursor ->
                val activityNameIndex = cursor.getColumnIndex(COLUMN_ACTIVITY_NAME)
                val activityDescriptionIndex = cursor.getColumnIndex(COLUMN_ACTIVITY_DESCRIPTION)
                val activityColorLabelIndex = cursor.getColumnIndex(COLUMN_ACTIVITY_COLOR_LABEL)

                while (cursor.moveToNext()) {
                    val activityName = cursor.getString(activityNameIndex)
                    val activityDescription = cursor.getString(activityDescriptionIndex)
                    val activityColorLabel = cursor.getString(activityColorLabelIndex)

                    val activity = ActivityItem(activityName, activityDescription, activityColorLabel)
                    activitiesList.add(activity)
                }
            }
        } catch (e: Exception) {
            Log.e("ActivityDBHelper", "Error fetching activities", e)
        } finally {
            innerCursor?.close()
            db.close()
        }

        Log.d("ActivityDBHelper", "Retrieved activities from database: $activitiesList")
        return activitiesList
    }
}
