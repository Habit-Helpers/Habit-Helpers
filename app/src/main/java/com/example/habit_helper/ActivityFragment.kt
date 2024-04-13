package com.example.habit_helper


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ActivityFragment : Fragment() {

    private lateinit var recyclerViewActivities: RecyclerView
    private lateinit var addActivityButton: Button
    private lateinit var emptyView: TextView
    private val viewModel: ActivityViewModel by viewModels()
    private lateinit var adapter: ActivityAdapter

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
        recyclerViewActivities = view.findViewById(R.id.recyclerViewActivities) // Updated variable name
        addActivityButton = view.findViewById(R.id.addActivityButton)
        emptyView = view.findViewById(R.id.emptyView)


        Log.d("ActivityFragment", "RecyclerView initialized: ${true}")


        // Set up RecyclerView adapter
        adapter = ActivityAdapter(emptyList())
        recyclerViewActivities.adapter = adapter
        recyclerViewActivities.layoutManager = LinearLayoutManager(context)

        // Observe the list of activities from the ViewModel
        viewModel.activityList.observe(viewLifecycleOwner) { activities ->
            Log.d("ActivityFragment", "Received new activity list: $activities")

            if (activities.isEmpty()) {
                Log.d("ActivityFragment", "RecyclerView is hidden, empty view is visible")
                recyclerViewActivities.visibility = View.GONE
                emptyView.visibility = View.VISIBLE
            } else {
                Log.d("ActivityFragment", "RecyclerView is visible, empty view is hidden")
                recyclerViewActivities.visibility = View.VISIBLE
                emptyView.visibility = View.GONE
                adapter.updateActivities(activities)
                Log.d("ActivityFragment", "Adapter updated with new activities: $activities")

            }
        }

        // Set up OnClickListener for addActivityButton
        addActivityButton.setOnClickListener {
            // Navigate to the AddActivityFragment
            findNavController().navigate(R.id.action_activityFragment_to_addActivityFragment)
        }
    }
}

