package com.example.habit_helper

import android.annotation.SuppressLint
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
    private lateinit var recyclerViewUserGoals: RecyclerView

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_insights, container, false)
        Log.d(TAG, "Fragment view inflated")

        // Initialize RecyclerView for recommended changes
        val recyclerViewRecommendedChanges = view.findViewById<RecyclerView>(R.id.recyclerViewRecommendedChanges)
        val recommendedChangesList = getRandomRecommendations()
        val recommendedChangesAdapter = RecommendedChangesAdapter(recommendedChangesList)
        recyclerViewRecommendedChanges.adapter = recommendedChangesAdapter
        recyclerViewRecommendedChanges.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        Log.d(TAG, "Recommended changes RecyclerView initialized")

        // Initialize RecyclerView for user goals
        recyclerViewUserGoals = view.findViewById(R.id.recyclerViewUserGoals)

        // Initialize userGoalsAdapter
        userGoalsAdapter = UserGoalsAdapter(mutableListOf(), true)

        recyclerViewUserGoals.adapter = userGoalsAdapter
        recyclerViewUserGoals.layoutManager = LinearLayoutManager(context)
        Log.d(TAG, "User goals RecyclerView initialized")

        // Initialize dbHelper
        dbHelper = GoalsDatabaseHelper(requireContext())
        Log.d(TAG, "Database helper initialized")

        // Fetch goals from the database and update the adapter
        val goals = dbHelper.getAllGoals()
        userGoalsAdapter.updateGoals(goals)
        Log.d(TAG, "Goals fetched from database")

        // Set a click listener on the add goal button
        view.findViewById<Button>(R.id.buttonAddGoal).setOnClickListener {
            // Navigate to the Add New Goals fragment
            findNavController().navigate(R.id.action_insightsFragment_to_addNewGoalFragment)
            Log.d(TAG, "Add goal button clicked")
        }

        return view
    }

    private fun getRandomRecommendations(): List<String> {
        // List of recommended changes (can be new habits or activities to try)
        val recommendedChanges = mutableListOf(
            "Drink a glass of water as soon as you wake up.",
            "Take a 10-minute walk after lunch.",
            "Practice gratitude journaling before bedtime.",
            "Incorporate stretching exercises into your morning routine.",
            "Limit screen time before going to bed.",
            "Try meditation for 5 minutes each day.",
            "Read for at least 20 minutes every evening.",
            "Practice deep breathing exercises when feeling stressed.",
            "Plan your meals for the week ahead on Sundays.",
            "Practice mindful eating during meals.",
            "Visit a local museum or art gallery.",
            "Attend a yoga or fitness class.",
            "Cook a new recipe from a different cuisine.",
            "Explore a nearby hiking trail.",
            "Volunteer for a community service project.",
            "Join a book club or discussion group.",
            "Take a dance or music class.",
            "Visit a farmers' market and try new fruits or vegetables.",
            "Attend a live concert or performance.",
            "Participate in a DIY or crafting workshop."
        )

        // Shuffle the list to randomize the order
        recommendedChanges.shuffle()

        // Return a sublist with a maximum of 3 recommendations
        return recommendedChanges.subList(0, minOf(recommendedChanges.size, 5))
    }


    companion object {
        private const val TAG = "InsightsFragment"
    }
}


