package com.example.habit_helper

import android.util.Log

// TaskRepository.kt
class TaskRepository(private val taskDao: TaskDao) {

    suspend fun insert(task: Task) {
        Log.d("TaskRepository", "Inserting task: $task")

        taskDao.insert(task)
    }
}
