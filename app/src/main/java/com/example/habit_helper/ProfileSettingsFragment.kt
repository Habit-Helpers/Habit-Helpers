package com.example.habit_helper

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class ProfileSettingsFragment : Fragment() {

    private lateinit var dbHelper: MyDBHelper

    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var weightEditText: EditText
    private lateinit var heightEditText: EditText
    private lateinit var editInfoButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("ProfileSettingsFragment", "onCreateView")
        val view = inflater.inflate(R.layout.fragment_profile_settings, container, false)

        dbHelper = MyDBHelper(requireContext())

        // Initialize views
        usernameEditText = view.findViewById(R.id.editTextProfileUsername)
        emailEditText = view.findViewById(R.id.editTextProfileEmail)
        passwordEditText = view.findViewById(R.id.editTextProfilePassword)
        weightEditText = view.findViewById(R.id.editTextProfileWeight)
        heightEditText = view.findViewById(R.id.editTextProfileHeight)
        editInfoButton = view.findViewById(R.id.buttonEditProfile)

        // Retrieve user email from wherever it's stored in your app
        val userEmail = getUserEmail() // You need to implement this method
        Log.d("ProfileSettingsFragment", "User email: $userEmail")

        // Retrieve user data from the database and populate the EditText fields
        val userData = dbHelper.getUserData(userEmail) // You need to implement this method in MyDBHelper
        userData?.let {
            usernameEditText.setText(it.username)
            emailEditText.setText(it.email)
            passwordEditText.setText(it.password)
            weightEditText.setText(it.weight.toString())
            heightEditText.setText(it.height.toString())
            Log.d("ProfileSettingsFragment", "User data retrieved: $it")
        }


        editInfoButton.setOnClickListener {
            Log.d("ProfileSettingsFragment", "Edit info button clicked")
            // Navigate to the fragment where the user can edit their information
            findNavController().navigate(R.id.action_profileSettingsFragment_to_settingsFragment)
        }

        return view
    }

    private fun getUserEmail(): String {
        // Example: Retrieve the user's email from SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("user_email", "") ?: ""
    }
}
