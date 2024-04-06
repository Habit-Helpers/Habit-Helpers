package com.example.habit_helper

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Call displayUserData() to populate UI elements with saved data
        displayUserData()

        // Set up OnClickListener for the Save button to save user data
        val saveButton = view.findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            // Retrieve user input from EditText fields
            val username = view.findViewById<EditText>(R.id.editTextUsername).text.toString()
            val email = view.findViewById<EditText>(R.id.editTextEmail).text.toString()
            val weight = view.findViewById<EditText>(R.id.editTextWeight).text.toString()
            val height = view.findViewById<EditText>(R.id.editTextHeight).text.toString()

            // Save user data
            saveUserData(username, email, weight, height)
        }
    }

    // Function to save user data using SharedPreferences
    private fun saveUserData(username: String, email: String, weight: String, height: String) {
        val sharedPrefs = requireContext().getSharedPreferences("userData", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putString("username", username)
        editor.putString("email", email)
        editor.putString("weight", weight)
        editor.putString("height", height)
        editor.apply()
    }

    // Function to display user data
    private fun displayUserData() {
        val sharedPrefs = requireContext().getSharedPreferences("userData", Context.MODE_PRIVATE)
        val username = sharedPrefs.getString("username", "")
        val email = sharedPrefs.getString("email", "")
        val weight = sharedPrefs.getString("weight", "")
        val height = sharedPrefs.getString("height", "")

        // Populate UI elements
        view?.findViewById<EditText>(R.id.editTextUsername)?.setText(username)
        view?.findViewById<EditText>(R.id.editTextEmail)?.setText(email)
        view?.findViewById<EditText>(R.id.editTextWeight)?.setText(weight)
        view?.findViewById<EditText>(R.id.editTextHeight)?.setText(height)
    }
}
