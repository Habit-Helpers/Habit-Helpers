package com.example.habit_helper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class AddNewGoalFragment : Fragment() {

    private lateinit var dbHelper: GoalsDatabaseHelper
    private lateinit var goalNameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var statusRadioGroup: RadioGroup
    private lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_new_goal, container, false)

        // Initialize Views
        goalNameEditText = view.findViewById(R.id.goalNameEditText)
        descriptionEditText = view.findViewById(R.id.descriptionEditText)
        statusRadioGroup = view.findViewById(R.id.statusRadioGroup)
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
        // Retrieve goal name and description from EditText
        val goalName = goalNameEditText.text.toString()
        val goalDescription = descriptionEditText.text.toString()

        // Determine the selected status from the RadioGroup
        val selectedRadioButtonId = statusRadioGroup.checkedRadioButtonId
        val goalStatus = when (selectedRadioButtonId) {
            R.id.completedRadioButton -> "Completed"
            R.id.toDoRadioButton -> "To Do"
            R.id.inProgressRadioButton -> "In Progress"
            else -> ""
        }

        // Add goal to database
        dbHelper.addGoal(goalName, goalDescription, goalStatus)

        // Show a toast message or perform any other action if needed
        Toast.makeText(requireContext(), "Goal saved: $goalName", Toast.LENGTH_SHORT).show()

        // Navigate back to previous fragment
        findNavController().navigateUp()
    }
}

