package com.example.habit_helper

data class Goal(
    val name: String,
    val description: String,
    val status: String,
    var isExpanded: Boolean = false // Property to track expanded state, initialized to false
)

