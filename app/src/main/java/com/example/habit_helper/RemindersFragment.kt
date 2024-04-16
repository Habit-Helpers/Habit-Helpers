package com.example.habit_helper

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RemindersFragment : Fragment() {

    private lateinit var calendarView: CalendarView
    private lateinit var recyclerViewReminders: RecyclerView
    private lateinit var addNewButton: Button
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var databaseHelper: TasksDatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reminders, container, false)

        calendarView = view.findViewById(R.id.calendarView)
        recyclerViewReminders = view.findViewById(R.id.recyclerViewReminders)
        addNewButton = view.findViewById(R.id.button2)

        recyclerViewReminders.layoutManager = LinearLayoutManager(requireContext())

        taskAdapter = TaskAdapter(emptyList()) // Initialize TaskAdapter with empty list
        recyclerViewReminders.adapter = taskAdapter // Set adapter to RecyclerView

        databaseHelper = TasksDatabaseHelper(requireContext())

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$year-${month + 1}-$dayOfMonth"
            Log.d("RemindersFragment", "Selected date: $selectedDate")
            // If needed, you can query tasks for the selected date from the database here
        }

        addNewButton.setOnClickListener {
            // Navigate to the AddTaskFragment when the button is clicked
            findNavController().navigate(R.id.action_remindersFragment_to_addTaskFragment)
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        // Load tasks from the database and update the RecyclerView
        loadTasks()
    }

    private fun loadTasks() {
        val tasks = databaseHelper.getAllTasks().reversed() // Reverse the order of tasks
        taskAdapter.updateTasks(tasks)
    }

    // This method will be called by AddTaskFragment to add a new task
    fun addTask(task: Task) {
        databaseHelper.addTask(task.name, task.date)
        loadTasks() // Reload tasks after adding a new task
    }
}
