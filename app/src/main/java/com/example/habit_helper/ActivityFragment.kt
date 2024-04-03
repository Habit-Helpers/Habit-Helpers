package com.example.habit_helper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class ActivityFragment : Fragment() {

    private lateinit var recyclerViewGoals: RecyclerView
    private lateinit var addActivityButton: Button
    private lateinit var emptyView: TextView

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

        // Set up OnClickListener for addActivityButton
        addActivityButton.setOnClickListener {
            // Handle button click, for example, navigate to a new fragment
            // You can replace 'YourFragmentToAddActivity' with the fragment you want to navigate to
            // findNavController().navigate(R.id.action_activityFragment_to_AddActivity)
        }


    }
}
