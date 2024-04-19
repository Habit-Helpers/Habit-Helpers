package com.example.habit_helper

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

class ActivityFragment : Fragment() {

    private lateinit var recyclerViewActivities: RecyclerView
    private lateinit var addActivityButton: Button
    private lateinit var emptyView: TextView
    private lateinit var collapseButton: Button // Added
    private lateinit var adapter: ActivityAdapter
    private lateinit var dbHelper: ActivityDBHelper

    private var isExpanded = false // Added

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

        // Find views
        recyclerViewActivities = view.findViewById(R.id.recyclerViewActivities)
        addActivityButton = view.findViewById(R.id.addActivityButton)
        emptyView = view.findViewById(R.id.emptyView)
        collapseButton = view.findViewById(R.id.collapseButton) // Added
        Log.d("ActivityFragment", "Views initialized")

        // Initialize DBHelper
        dbHelper = ActivityDBHelper(requireContext())
        Log.d("ActivityFragment", "DBHelper initialized")

        // Set up RecyclerView adapter
        adapter = ActivityAdapter(emptyList()) {
            // Toggle the expansion/collapse of RecyclerView
            isExpanded = !isExpanded
            toggleRecyclerViewHeight()
        }
        recyclerViewActivities.adapter = adapter
        recyclerViewActivities.layoutManager = LinearLayoutManager(context)
        Log.d("ActivityFragment", "RecyclerView adapter set")

        // Retrieve data from the database
        val activities = dbHelper.getAllActivities().reversed() // Reverse the list
        if (activities.isEmpty()) {
            // Show empty view if no data available
            recyclerViewActivities.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
            Log.d("ActivityFragment", "No activities found, showing empty view")
        } else {
            // Update RecyclerView with retrieved data
            recyclerViewActivities.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
            adapter.updateActivities(activities)
            Log.d("ActivityFragment", "Activities retrieved and RecyclerView updated")
        }

        // Set up OnClickListener for addActivityButton
        addActivityButton.setOnClickListener {
            // Navigate to the AddActivityFragment
            findNavController().navigate(R.id.action_activityFragment_to_addActivityFragment)
            Log.d("ActivityFragment", "AddActivityButton clicked")
        }

        // Set click listener to collapse the expanded RecyclerView
        collapseButton.setOnClickListener {
            isExpanded = false
            toggleRecyclerViewHeight()
        }
    }

    private fun toggleRecyclerViewHeight() {
        val newHeight = if (isExpanded) ViewGroup.LayoutParams.WRAP_CONTENT else 0
        recyclerViewActivities.layoutParams.height = newHeight
        recyclerViewActivities.requestLayout()
    }
}


