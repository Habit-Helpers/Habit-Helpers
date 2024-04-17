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
/*
import lecho.lib.hellocharts.model.Line
import lecho.lib.hellocharts.model.LineChartData
import lecho.lib.hellocharts.model.PointValue
import lecho.lib.hellocharts.util.ChartUtils
import lecho.lib.hellocharts.view.LineChartView

 */

class InsightsFragment : Fragment() {

    private lateinit var userGoalsAdapter: UserGoalsAdapter
    private lateinit var dbHelper: GoalsDatabaseHelper
    /*
    private lateinit var chart: LineChartView
     */

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_insights, container, false)

        // Initialize RecyclerView
        val recyclerViewRecommendedChanges = view.findViewById<RecyclerView>(R.id.recyclerViewRecommendedChanges)

        // Sample data for recommended changes
        val recommendedChangesList = getRandomRecommendations()

        // Set up recommended changes RecyclerView
        val recommendedChangesAdapter = RecommendedChangesAdapter(recommendedChangesList)
        recyclerViewRecommendedChanges.adapter = recommendedChangesAdapter
        recyclerViewRecommendedChanges.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Initialize user goals RecyclerView and adapter
        userGoalsAdapter = UserGoalsAdapter(emptyList())

        // Initialize dbHelper
        dbHelper = GoalsDatabaseHelper(requireContext())

        // Fetch goals from the database and update the adapter
        val goals = dbHelper.getAllGoals()
        val reversedGoals = goals.reversed()
        userGoalsAdapter.updateGoals(reversedGoals)

        /* Initialize chart
        chart = view.findViewById(R.id.chart)
        setupChart()
        */
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
    /*
    private fun setupChart() {
        val numPoints = 7 // Number of data points
        val values = mutableListOf<PointValue>()

        // Generate random values for the chart
        for (i in 0 until numPoints) {
            val value = (Math.random() * 100).toFloat() // Random value between 0 and 100
            values.add(PointValue(i.toFloat(), value))
        }

        // Create a line and add data to it
        val line = Line(values)
            .setColor(ChartUtils.COLOR_GREEN)
            .setHasPoints(true)
            .setFilled(true)
            .setHasLabels(true)

        // Create a list containing only one line
        val lines = mutableListOf<Line>()
        lines.add(line)

        // Create a LineChartData object with the lines
        val data = LineChartData()
        data.lines = lines

        // Set the chart data
        chart.lineChartData = data
    }*/
}
