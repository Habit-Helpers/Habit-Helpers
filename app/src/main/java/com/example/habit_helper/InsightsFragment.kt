package com.example.habit_helper

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InsightsFragment : Fragment() {

    private lateinit var dbHelper: GoalsDatabaseHelper
    private lateinit var userGoalsAdapter: UserGoalsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_insights, container, false)

        // Initialize RecyclerViews
        val recyclerViewRecommendedChanges = view.findViewById<RecyclerView>(R.id.recyclerViewRecommendedChanges)

        // Sample data for recommended changes
        val recommendedChangesList = listOf("Change 1", "Change 2", "Change 3")

        // Set up recommended changes RecyclerView
        val recommendedChangesAdapter = RecommendedChangesAdapter(recommendedChangesList)
        recyclerViewRecommendedChanges.adapter = recommendedChangesAdapter
        recyclerViewRecommendedChanges.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Initialize database helper
        dbHelper = GoalsDatabaseHelper(requireContext())

        // Initialize user goals RecyclerView and adapter
        val recyclerViewUserGoals = view.findViewById<RecyclerView>(R.id.recyclerViewUserGoals)
        userGoalsAdapter = UserGoalsAdapter(emptyList())
        recyclerViewUserGoals.adapter = userGoalsAdapter
        recyclerViewUserGoals.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("InsightsFragment", "onCreate() method called")

        // Set a click listener on the add goal button
        view.findViewById<Button>(R.id.buttonAddGoal).setOnClickListener {
            // Navigate to the Add New Goals fragment
            findNavController().navigate(R.id.action_insightsFragment_to_addNewGoalFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        // Reload and display user goals
        loadUserGoals()
    }


    private fun loadUserGoals() {
        // Retrieve user goals from the database
        Log.d("InsightsFragment", "Loading user goals from the database")
        var userGoalsList: List<Goal> = dbHelper.getAllGoals()
        Log.d("InsightsFragment", "User goals retrieved from database: $userGoalsList")

        // Reverse the order of the user goals list
        userGoalsList = userGoalsList.reversed()

        // Update the RecyclerView adapter with the loaded user goals
        userGoalsAdapter.updateGoals(userGoalsList)
    }

}

