package com.example.habit_helper
// HomeFragment.kt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize BottomNavigationView
        bottomNavigationView = view.findViewById(R.id.bottom_navigation)

        // Set item selected listener
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

