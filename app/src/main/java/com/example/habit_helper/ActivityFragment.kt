package com.example.habit_helper

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
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
    private lateinit var collapseButton: Button
    private lateinit var adapter: ActivityAdapter
    private lateinit var dbHelper: ActivityDBHelper

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

        dbHelper = ActivityDBHelper(requireContext())

        adapter = ActivityAdapter(emptyList()) {
            // Toggle the expansion/collapse of RecyclerView
            isExpanded = !isExpanded
            toggleRecyclerViewHeight()
            collapseButton.visibility = if (isExpanded) View.VISIBLE else View.GONE
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
        }





        collapseButton.setOnClickListener {
            isExpanded = false
            toggleRecyclerViewHeight()
            collapseButton.visibility = View.GONE
            Log.d("ActivityFragment", "Collapse button clicked")
        }
    }

    private fun toggleRecyclerViewHeight() {
        val newHeight = if (isExpanded) ViewGroup.LayoutParams.WRAP_CONTENT else 0
        recyclerViewActivities.layoutParams.height = newHeight
        recyclerViewActivities.requestLayout()
        Log.d("ActivityFragment", "Toggled RecyclerView height")
    }
}
