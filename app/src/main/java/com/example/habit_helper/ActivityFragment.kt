package com.example.habit_helper

import android.os.Bundle
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

    private lateinit var recyclerViewGoals: RecyclerView
    private lateinit var addActivityButton: Button
    private lateinit var emptyView: TextView

    // Example list of activities
    private val activityList = listOf(
        ActivityItem("Activity 1", "Description 1", "Color 1"),
        ActivityItem("Activity 2", "Description 2", "Color 2"),
        // Add more activity items as needed
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find views
        recyclerViewGoals = view.findViewById(R.id.recyclerViewGoals)
        addActivityButton = view.findViewById(R.id.addActivityButton)
        emptyView = view.findViewById(R.id.emptyView)

        // Set up RecyclerView
        val adapter = ActivityAdapter(activityList)
        recyclerViewGoals.adapter = adapter
        recyclerViewGoals.layoutManager = LinearLayoutManager(context)

        // Set up OnClickListener for addActivityButton
        addActivityButton.setOnClickListener {
            // Navigate to the AddActivityFragment
            findNavController().navigate(R.id.action_activityFragment_to_addActivityFragment)
        }
    }
}



