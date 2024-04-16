package com.example.habit_helper

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class AddActivityFragment : Fragment() {

    private lateinit var editTextActivityName: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var spinnerColorLabel: Spinner
    private lateinit var buttonSaveActivity: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find views
        editTextActivityName = view.findViewById(R.id.editTextActivityName)
        editTextDescription = view.findViewById(R.id.editTextDescription)
        spinnerColorLabel = view.findViewById(R.id.spinnerColorLabel)
        buttonSaveActivity = view.findViewById(R.id.buttonSaveActivity)

        // Set up color label spinner
        val colors = arrayOf("Red", "Blue", "Green", "Yellow") // Example list of colors
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, colors)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerColorLabel.adapter = adapter

        // Set up OnClickListener for the save button
        buttonSaveActivity.setOnClickListener {
            // Validate user input and save the activity data
            saveActivity()
        }
    }

    private fun saveActivity() {
        val activityName = editTextActivityName.text.toString().trim()
        val activityDescription = editTextDescription.text.toString().trim()
        val selectedColor = spinnerColorLabel.selectedItem.toString()

        // Create an ActivityItem object with the user's input
        val newActivity = ActivityItem(activityName, activityDescription, selectedColor)

        // Add the new activity to the database
        val dbHelper = ActivityDBHelper(requireContext())
        dbHelper.addActivity(newActivity)

        Log.d("AddActivityFragment", "New activity saved: $newActivity")

        // Navigate back to the ActivitiesFragment
        findNavController().navigateUp()
    }
}
