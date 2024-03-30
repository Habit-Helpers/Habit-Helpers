package com.example.habit_helper

// UserDao.kt

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: UserEntity)
}
