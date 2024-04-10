package com.example.habit_helper

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TaskRepository

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
    }

    fun insertTask(task: Task) {
        Log.d("TaskViewModel", "Inserting task: $task")

        viewModelScope.launch {
            repository.insert(task)
        }
    }
}

