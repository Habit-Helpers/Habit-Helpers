package com.example.habit_helper
// MainActivity.kt

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Optional: Handle button clicks
        val loginButton = findViewById<Button>(R.id.button_login)
        val signUpButton = findViewById<Button>(R.id.button_signup)

        loginButton.setOnClickListener {
            // Navigate to the login screen using the Navigation component
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_main_to_login)
        }

        signUpButton.setOnClickListener {
            // Navigate to the sign-up screen using the Navigation component
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_main_to_signup)
        }
    }


}
