package com.example.habit_helper

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RemindersFragment : Fragment() {

    // Define your views
    private lateinit var calendarView: CalendarView
    private lateinit var recyclerViewReminders: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reminders, container, false)

        // Initialize views
        calendarView = view.findViewById(R.id.calendarView)
        recyclerViewReminders = view.findViewById(R.id.recyclerViewReminders)

        // Set up calendar view
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // Handle date selection here
            // You can load reminders for the selected date and update the RecyclerView accordingly
        }

        // Set up RecyclerView
        val remindersList = listOf("Reminder 1", "Reminder 2", "Reminder 3") // Example list of reminders
        val adapter = RemindersAdapter(remindersList)
        recyclerViewReminders.adapter = adapter

        return view
    }


}

