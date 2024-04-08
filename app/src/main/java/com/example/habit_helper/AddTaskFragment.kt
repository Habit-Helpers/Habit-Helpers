package com.example.habit_helper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

class AddTaskFragment : Fragment() {

    // Initialize TaskViewModel using viewModels delegate
    private val viewModel: TaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up OnClickListener for Save button
        val saveButton: Button = view.findViewById(R.id.saveButton)
        saveButton.setOnClickListener {
            // Retrieve task data from input fields
            val taskName: String = view.findViewById<EditText>(R.id.taskNameEditText).text.toString()
            val taskDate: String = view.findViewById<EditText>(R.id.taskDateEditText).text.toString()

            // Save task data to database or data source
            val newTask = Task(taskName, taskDate)
            viewModel.insertTask(newTask)

            // Navigate back to the previous fragment (assuming Reminders fragment)
            findNavController().navigateUp()
        }
    }

}
