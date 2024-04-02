package com.example.habit_helper


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextUsername = view.findViewById<EditText>(R.id.edit_text_username)
        val editTextEmail = view.findViewById<EditText>(R.id.edit_text_email)
        val editTextPassword = view.findViewById<EditText>(R.id.edit_text_password)
        val editTextConfirmPassword = view.findViewById<EditText>(R.id.edit_text_confirm_password)
        val buttonSignUp = view.findViewById<Button>(R.id.button_sign_up)
        val textLoginLink = view.findViewById<TextView>(R.id.loginlink)

        buttonSignUp.setOnClickListener {
            val username = editTextUsername.text.toString()
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val confirmPassword = editTextConfirmPassword.text.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                // Display an error message indicating that all fields are required
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                // Display an error message indicating that passwords do not match
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            performSignUp(username, email, password)

        }

        textLoginLink.setOnClickListener {
            findNavController().navigate(R.id.action_signup_to_login)
        }
    }

    private fun performSignUp(username: String, email: String, password: String) {
        val dbHelper = MyDBHelper(requireContext().applicationContext)
        val db = dbHelper.writableDatabase

        try {
            // Check if user already exists
            val userExists = dbHelper.checkUser(email)
            if (userExists) {
                // User already exists, handle accordingly (e.g., show error message)
                Toast.makeText(requireContext(), "User already exists", Toast.LENGTH_SHORT).show()
                return
            }

            // Add new user to the database
            val result = dbHelper.addUser(username, email, password)
            if (result != -1L) {
                // Handle successful signup (e.g., navigate to the home screen)
                Toast.makeText(requireContext(), "Sign up successful", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_signup_to_home)
            } else {
                // Handle unsuccessful signup
                Toast.makeText(requireContext(), "Sign up failed", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            // Handle exceptions (e.g., database errors)
            Toast.makeText(requireContext(), "Sign up failed", Toast.LENGTH_SHORT).show()
        } finally {
            db.close()
        }
    }


}





