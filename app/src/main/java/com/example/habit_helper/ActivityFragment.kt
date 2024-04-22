package com.example.habit_helper

// ActivityFragment.kt

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ActivityFragment : Fragment(), PieChartView.OnColorSegmentClickListener {

    private lateinit var recyclerViewActivities: RecyclerView
    private lateinit var addActivityButton: Button
    private lateinit var emptyView: TextView
    private lateinit var collapseButton: Button
    private lateinit var adapter: ActivityAdapter
    private lateinit var dbHelper: ActivityDBHelper
    private lateinit var pieChartView: PieChartView

    private var isExpanded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("ActivityFragment", "onViewCreated")

        recyclerViewActivities = view.findViewById(R.id.recyclerViewActivities)
        addActivityButton = view.findViewById(R.id.addActivityButton)
        emptyView = view.findViewById(R.id.emptyView)
        collapseButton = view.findViewById(R.id.collapseButton)
        pieChartView = view.findViewById(R.id.pieChart)

        dbHelper = ActivityDBHelper(requireContext())

        adapter = ActivityAdapter(emptyList()) {
            // Toggle the expansion/collapse of RecyclerView
            isExpanded = !isExpanded
            toggleRecyclerViewHeight()
            collapseButton.visibility = if (isExpanded) View.VISIBLE else View.GONE
            updatePieChart()
        }
        recyclerViewActivities.adapter = adapter
        recyclerViewActivities.layoutManager = LinearLayoutManager(context)

        val activities = dbHelper.getAllActivities().reversed()
        if (activities.isEmpty()) {
            recyclerViewActivities.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
            Log.d("ActivityFragment", "No activities found, showing empty view")
        } else {
            recyclerViewActivities.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
            adapter.updateActivities(activities)
            Log.d("ActivityFragment", "Activities retrieved and RecyclerView updated")
            updatePieChart()
        }

        addActivityButton.setOnClickListener {
            findNavController().navigate(R.id.action_activityFragment_to_addActivityFragment)
            Log.d("ActivityFragment", "AddActivityButton clicked")
        }

        recyclerViewActivities.setOnClickListener {
            // Toggle the expansion/collapse of RecyclerView
            isExpanded = !isExpanded
            toggleRecyclerViewHeight()
            collapseButton.visibility = if (isExpanded) View.VISIBLE else View.GONE
            Log.d("ActivityFragment", "RecyclerView clicked")
            updatePieChart()
        }

        collapseButton.setOnClickListener {
            isExpanded = false
            toggleRecyclerViewHeight()
            collapseButton.visibility = View.GONE
            Log.d("ActivityFragment", "Collapse button clicked")
            updatePieChart()
        }

        // Set click listener for color segments in the pie chart
        pieChartView.onColorSegmentClickListener = this
    }

    override fun onColorSegmentClick(colorLabel: String) {
        // Filter activities based on the selected color label
        val activities = dbHelper.getAllActivities().filter { it.colorLabel == colorLabel }
        adapter.updateActivities(activities)
    }

    private fun updatePieChart() {
        val activities = dbHelper.getAllActivities()
        val colorMap = mutableMapOf<String, Float>()
        var totalActivities = 0

        // Count activities for each color tag
        for (activity in activities) {
            val colorTag = activity.colorLabel
            val count = colorMap[colorTag] ?: 0f
            colorMap[colorTag] = count + 1
            totalActivities++
        }

        // Convert count to percentage
        val pieChartData = colorMap.map { entry ->
            val percentage = entry.value / totalActivities * 100
            entry.key to percentage
        }

        // Update the pie chart with the new data
        pieChartView.setChartData(pieChartData)
    }

    private fun toggleRecyclerViewHeight() {
        val newHeight = if (isExpanded) ViewGroup.LayoutParams.WRAP_CONTENT else 0
        recyclerViewActivities.layoutParams.height = newHeight
        recyclerViewActivities.requestLayout()
        Log.d("ActivityFragment", "Toggled RecyclerView height")
    }
}
