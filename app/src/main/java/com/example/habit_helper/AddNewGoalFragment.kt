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

    private lateinit var dbHelper: GoalsDatabaseHelper
    private lateinit var goalNameEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_new_goal, container, false)

        // Initialize Views
        goalNameEditText = view.findViewById(R.id.goalNameEditText)
        saveButton = view.findViewById(R.id.saveButton)

        // Initialize Database Helper
        dbHelper = GoalsDatabaseHelper(requireContext())

        // Set up OnClickListener for saveButton
        saveButton.setOnClickListener {
            saveGoal()
        }

        return view
    }

    private fun saveGoal() {
        // Retrieve goal name from EditText
        val goalName = goalNameEditText.text.toString()

        // Add goal to database
        dbHelper.addGoal(goalName)

        // Show a toast message or perform any other action if needed
        Toast.makeText(requireContext(), "Goal saved: $goalName", Toast.LENGTH_SHORT).show()


        // Navigate back to previous fragment
        findNavController().navigateUp()
    }
}

