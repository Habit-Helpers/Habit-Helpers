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

class SettingsFragment : Fragment() {

    private lateinit var dbHelper: MyDBHelper

    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var weightEditText: EditText
    private lateinit var heightEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("SettingsFragment", "onCreateView")

        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        dbHelper = MyDBHelper(requireContext())

        // Initialize views
        usernameEditText = view.findViewById(R.id.editTextUsername)
        emailEditText = view.findViewById(R.id.editTextEmail)
        passwordEditText = view.findViewById(R.id.editTextPassword)
        weightEditText = view.findViewById(R.id.editTextWeight)
        heightEditText = view.findViewById(R.id.editTextHeight)

        val resetPasswordButton = view.findViewById<Button>(R.id.buttonResetPassword)
        val saveButton = view.findViewById<Button>(R.id.saveButton)
        val deleteAccountButton = view.findViewById<Button>(R.id.buttonDeleteAccount)

        resetPasswordButton.setOnClickListener {
            Log.d("SettingsFragment", "Reset password button clicked")

            // Implement reset password functionality
            // This might include sending a password reset email to the user's email address
        }

        saveButton.setOnClickListener {
            Log.d("SettingsFragment", "Save button clicked")

            // Implement save user data functionality
            saveUserData()
        }

        deleteAccountButton.setOnClickListener {
            Log.d("SettingsFragment", "Delete account button clicked")

            // Implement account deletion functionality
            // This might include showing a confirmation dialog
            // and deleting the user's account from the database
        }

        return view
    }

    private fun saveUserData() {
        Log.d("SettingsFragment", "saveUserData")

        // Get data from EditText fields
        val username = usernameEditText.text.toString()
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val weight = weightEditText.text.toString().toDoubleOrNull() ?: 0.0
        val height = heightEditText.text.toString().toDoubleOrNull() ?: 0.0

        Log.d("SettingsFragment", "Username: $username, Email: $email, Password: $password, Weight: $weight, Height: $height")

        // Update user data in the database
        dbHelper.updateUserProfileData(email, username, password, weight, height)

        findNavController().navigateUp()
    }
}
