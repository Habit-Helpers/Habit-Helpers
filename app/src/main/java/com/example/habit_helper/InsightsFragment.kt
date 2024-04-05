package com.example.habit_helper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InsightsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_insights, container, false)

        // Initialize RecyclerViews
        val recyclerViewRecommendedChanges = view.findViewById<RecyclerView>(R.id.recyclerViewRecommendedChanges)
        val recyclerViewUserGoals = view.findViewById<RecyclerView>(R.id.recyclerViewUserGoals)

        // Sample data for recommended changes and user goals
        val recommendedChangesList = listOf("Change 1", "Change 2", "Change 3")
        val userGoalsList = listOf("Goal 1", "Goal 2", "Goal 3")

        // Set up recommended changes RecyclerView
        val recommendedChangesAdapter = RecommendedChangesAdapter(recommendedChangesList)
        recyclerViewRecommendedChanges.adapter = recommendedChangesAdapter
        recyclerViewRecommendedChanges.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Set up user goals RecyclerView
        val userGoalsAdapter = UserGoalsAdapter(userGoalsList)
        recyclerViewUserGoals.adapter = userGoalsAdapter
        recyclerViewUserGoals.layoutManager = LinearLayoutManager(context)

        return view
    }
}

