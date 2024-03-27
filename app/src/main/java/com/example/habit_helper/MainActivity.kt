package com.example.habit_helper
// MainActivity.kt

// MainActivity.kt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    // Handle click on Home item
                    true
                }
                R.id.navigation_activities -> {
                    // Handle click on Activities item
                    true
                }
                R.id.navigation_reminders -> {
                    // Handle click on Reminders item
                    true
                }
                R.id.navigation_insights -> {
                    // Handle click on Insights item
                    true
                }
                else -> false
            }
        }
    }
}
