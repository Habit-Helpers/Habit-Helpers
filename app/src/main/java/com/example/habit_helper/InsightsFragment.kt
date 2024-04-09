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

    private lateinit var userGoalsAdapter: UserGoalsAdapter
    private lateinit var dbHelper: GoalsDatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_insights, container, false)

        // Initialize RecyclerView
        val recyclerViewRecommendedChanges = view.findViewById<RecyclerView>(R.id.recyclerViewRecommendedChanges)
        val recyclerViewUserGoals = view.findViewById<RecyclerView>(R.id.recyclerViewUserGoals)

        // Sample data for recommended changes
        val recommendedChangesList = listOf("Change 1", "Change 2", "Change 3")

        // Set up recommended changes RecyclerView
        val recommendedChangesAdapter = RecommendedChangesAdapter(recommendedChangesList)
        recyclerViewRecommendedChanges.adapter = recommendedChangesAdapter
        recyclerViewRecommendedChanges.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Initialize user goals RecyclerView and adapter
        userGoalsAdapter = UserGoalsAdapter(emptyList())
        recyclerViewUserGoals.adapter = userGoalsAdapter
        recyclerViewUserGoals.layoutManager = LinearLayoutManager(context)

        // Initialize dbHelper
        dbHelper = GoalsDatabaseHelper(requireContext())

        // Fetch goals from the database and update the adapter
        val goals = dbHelper.getAllGoals()
        userGoalsAdapter.updateGoals(goals)

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
}


