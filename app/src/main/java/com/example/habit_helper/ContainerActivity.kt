package com.example.habit_helper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class ContainerActivity : AppCompatActivity() {

    private lateinit var navControllerMain: NavController
    private lateinit var navControllerHome: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)

        // Find the NavHostFragment for the main fragment
        val navHostFragmentMain = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        navControllerMain = navHostFragmentMain.navController

        // Find the NavHostFragment for the home fragment
        val navHostFragmentHome = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_home) as NavHostFragment
        navControllerHome = navHostFragmentHome.navController

        // Set up BottomNavigationView with NavController for home fragment
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navControllerHome)
    }

    // Handle back press to navigate up or pop back stack
    override fun onSupportNavigateUp(): Boolean {
        return navControllerMain.navigateUp() || super.onSupportNavigateUp()
    }
}


