package com.example.habit_helper

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class AddTaskFragment : Fragment() {

    private lateinit var databaseHelper: TasksDatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("AddTaskFragment", "onViewCreated()")

        // Initialize the database helper
        databaseHelper = TasksDatabaseHelper(requireContext())

        // Set up OnClickListener for Save button
        val saveButton: Button = view.findViewById(R.id.saveButton)
        saveButton.setOnClickListener {

            val taskName = view.findViewById<EditText>(R.id.taskNameEditText).text.toString()
            val taskDate = view.findViewById<EditText>(R.id.taskDateEditText).text.toString()

            // Check if taskName and taskDate are not empty
            if (taskName.isNotEmpty() && taskDate.isNotEmpty()) {
                // Save task to the database
                saveTask(taskName, taskDate)
                Log.d("AddTaskFragment", "Task saved to database: $taskName, $taskDate")
            } else {
                Log.e("AddTaskFragment", "Task name or date is empty")
            }

            // Navigate back to the previous fragment (assuming Reminders fragment)
            findNavController().navigateUp()
        }
    }

    // This method saves the task to the database and notifies the parent fragment
    private fun saveTask(taskName: String, taskDate: String) {
        // Add task to the database
        databaseHelper.addTask(taskName, taskDate)

        // Find the parent fragment (RemindersFragment)
        val remindersFragment = parentFragment as? RemindersFragment
        remindersFragment?.addTask(Task(taskName, taskDate))
    }

}

