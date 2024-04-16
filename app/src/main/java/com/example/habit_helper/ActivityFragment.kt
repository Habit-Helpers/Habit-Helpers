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
    private lateinit var adapter: ActivityAdapter
    private lateinit var dbHelper: ActivityDBHelper

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

        // Initialize DBHelper
        dbHelper = ActivityDBHelper(requireContext())

        // Set up RecyclerView adapter
        adapter = ActivityAdapter(emptyList())
        recyclerViewActivities.adapter = adapter
        recyclerViewActivities.layoutManager = LinearLayoutManager(context)

        // Retrieve data from the database
        val activities = dbHelper.getAllActivities()
        if (activities.isEmpty()) {
            // Show empty view if no data available
            recyclerViewActivities.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
        } else {
            // Update RecyclerView with retrieved data
            recyclerViewActivities.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
            adapter.updateActivities(activities)
        }

        // Set up OnClickListener for addActivityButton
        addActivityButton.setOnClickListener {
            // Navigate to the AddActivityFragment
            findNavController().navigate(R.id.action_activityFragment_to_addActivityFragment)
        }
    }
}


