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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reminders, container, false)

        calendarView = view.findViewById(R.id.calendarView)
        recyclerViewReminders = view.findViewById(R.id.recyclerViewReminders)
        addNewButton = view.findViewById(R.id.button2)

        recyclerViewReminders.layoutManager = LinearLayoutManager(requireContext())

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$year-${month + 1}-$dayOfMonth"
            Log.d("RemindersFragment", "Selected date: $selectedDate")

            // For demonstration purposes, I'll use a sample list of reminders
            val remindersList = listOf("Reminder 1", "Reminder 2", "Reminder 3")
            updateRecyclerView(remindersList)
        }

        addNewButton.setOnClickListener {
            // Navigate to the AddTaskFragment when the button is clicked
            findNavController().navigate(R.id.action_remindersFragment_to_addTaskFragment)
        }

        return view
    }

    private fun updateRecyclerView(remindersList: List<String>) {
        Log.d("RemindersFragment", "Updating RecyclerView with ${remindersList.size} reminders")
        val adapter = RemindersAdapter(remindersList)
        recyclerViewReminders.adapter = adapter // Set the adapter here
    }
}

