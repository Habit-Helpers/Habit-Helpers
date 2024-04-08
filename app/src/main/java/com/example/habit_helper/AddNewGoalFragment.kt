package com.example.habit_helper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class AddNewGoalFragment : Fragment() {

    private lateinit var goalNameEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_new_goal, container, false)

        // Find views
        goalNameEditText = view.findViewById(R.id.goalNameEditText)
        saveButton = view.findViewById(R.id.saveButton)

        // Set up OnClickListener for saveButton
        saveButton.setOnClickListener {
            saveGoal()
        }

        return view
    }

    private fun saveGoal() {
        // Retrieve goal details from EditText
        val goalName = goalNameEditText.text.toString()

        // Validate input
        if (goalName.isEmpty()) {
            // Show error message if goal name is empty
            Toast.makeText(requireContext(), "Please enter a goal name", Toast.LENGTH_SHORT).show()
            return
        }

        // Save the goal (e.g., to a database)
        // For demonstration purposes, let's just show a toast message
        Toast.makeText(requireContext(), "Goal saved: $goalName", Toast.LENGTH_SHORT).show()

        // Navigate back to Insights fragment
        findNavController().navigateUp()
    }
}
