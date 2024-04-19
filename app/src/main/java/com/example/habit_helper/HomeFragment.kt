package com.example.habit_helper

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    private lateinit var recyclerViewUserGoals: RecyclerView
    private lateinit var userGoalsAdapter: UserGoalsAdapter
    private lateinit var goalsDatabaseHelper: GoalsDatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("HomeFragment", "onViewCreated")

        // Initialize the RecyclerView
        recyclerViewUserGoals = view.findViewById(R.id.recyclerViewUserGoals)
        userGoalsAdapter = UserGoalsAdapter(emptyList(),false)
        recyclerViewUserGoals.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewUserGoals.adapter = userGoalsAdapter

        // Initialize the GoalsDatabaseHelper
        goalsDatabaseHelper = GoalsDatabaseHelper(requireContext())

        // Call a function to fetch and display the goals
        displayUserGoals()

        // Find the summary cards views by their IDs
        val stepsCard = view.findViewById<LinearLayout>(R.id.linearLayout2)
        val sleepCard = view.findViewById<LinearLayout>(R.id.linearLayout3)
        val waterCard = view.findViewById<LinearLayout>(R.id.linearLayout4)
        val caloriesCard = view.findViewById<LinearLayout>(R.id.linearLayout5)

        // Set onClickListener for each summary card
        stepsCard.setOnClickListener {
            Log.d("HomeFragment", "Steps card clicked")
            navigateToActivityFragment()
        }

        sleepCard.setOnClickListener {
            Log.d("HomeFragment", "Sleep card clicked")
            navigateToActivityFragment()
        }

        waterCard.setOnClickListener {
            Log.d("HomeFragment", "Water card clicked")
            navigateToActivityFragment()
        }

        caloriesCard.setOnClickListener {
            Log.d("HomeFragment", "Calories card clicked")
            navigateToActivityFragment()
        }
    }

    // Function to navigate to the activity fragment
    private fun navigateToActivityFragment() {
        Log.d("HomeFragment", "Navigating to ActivityFragment")
        // Use the Navigation component to navigate to the activity fragment
        findNavController().navigate(R.id.action_homeFragment_to_activityFragment)
    }

    private fun displayUserGoals() {
        Log.d("HomeFragment", "Fetching goals from database")
        // Retrieve the list of goals from the database
        val goalsList = goalsDatabaseHelper.getAllGoals().toMutableList()

        // Update the adapter with the list of goals
        userGoalsAdapter.updateGoals(goalsList)
    }


}

