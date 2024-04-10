package com.example.habit_helper

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActivityViewModel : ViewModel() {
    private val _activityList = MutableLiveData<List<ActivityItem>>()
    val activityList: LiveData<List<ActivityItem>> = _activityList

    fun addActivity(activity: ActivityItem) {
        val currentList = _activityList.value.orEmpty().toMutableList()
        currentList.add(activity)
        _activityList.value = currentList

        Log.d("ActivityViewModel", "New activity added: $activity")
        Log.d("ActivityViewModel", "Updated activity list: ${_activityList.value}")
    }
}
