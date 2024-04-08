package com.example.habit_helper

// TaskRepository.kt
class TaskRepository(private val taskDao: TaskDao) {

    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }
}
