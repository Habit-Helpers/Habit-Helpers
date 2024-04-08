package com.example.habit_helper

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    val name: String,
    val date: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
