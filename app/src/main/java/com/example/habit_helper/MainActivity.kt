package com.example.habit_helper
// MainActivity.kt

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController

import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment,
                R.id.activitiesFragment,
                R.id.remindersFragment,
                R.id.insightsFragment,
                R.id.settingsFragment, -> showBottomNavigation()
                else -> hideBottomNavigation()
            }
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)

        // Handle bottom navigation item selection
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            try {
                when (menuItem.itemId) {
                    R.id.navigation_home -> {
                        navController.navigate(R.id.homeFragment)
                        true
                    }
                    R.id.navigation_activities -> {
                        // Navigate to ActivitiesFragment
                        navController.navigate(R.id.activitiesFragment)
                        true
                    }
                    R.id.navigation_reminders -> { // Add navigation for RemindersFragment
                        navController.navigate(R.id.remindersFragment)
                        true
                    }
                    R.id.navigation_insights -> { // Add navigation for RemindersFragment
                        navController.navigate(R.id.insightsFragment)
                        true
                    }
                    R.id.navigation_settings -> { // Add navigation for RemindersFragment
                        navController.navigate(R.id.settingsFragment)
                        true
                    }
                    else -> false
                }
            } catch (e: Exception) {
                Log.e("NavigationError", "Failed to navigate: ${e.message}")
                false
            }
        }
    }

    private fun showBottomNavigation() {
        findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.VISIBLE
    }

    private fun hideBottomNavigation() {
        findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.GONE
    }


}
