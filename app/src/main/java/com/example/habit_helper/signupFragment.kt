package com.example.habit_helper
import android.content.ContentValues
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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
                // You can show a Toast message or update a TextView with an error message
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                // Display an error message indicating that passwords do not match
                // You can show a Toast message or update a TextView with an error message
                return@setOnClickListener
            }

            // Perform sign-up logic here (e.g., send data to backend or save to local database)
            performSignUp(username, email, password)
        }

        textLoginLink.setOnClickListener {
            findNavController().navigate(R.id.action_signup_to_login)
        }
    }

    private fun performSignUp(username: String, email: String, password: String) {
        // Here you can put the code for sending sign-up data to the backend or saving locally
        // For example, using Retrofit for sending data to a backend server or Room for local database

        val dbHelper = MyDBHelper(requireContext().applicationContext)
        val db = dbHelper.writableDatabase

        try {
            // Check if user already exists
            val cursor = db.rawQuery("SELECT * FROM users WHERE username = ? OR email = ?", arrayOf(username, email))
            if (cursor.count > 0) {
                // User already exists, handle accordingly (e.g., show error message)
                cursor.close()
                return
            }
            cursor.close()

            // Insert new user into the database
            val values = ContentValues().apply {
                put("username", username)
                put("email", email)
                put("password", password)
            }
            db.insert("users", null, values)

            // Handle successful signup (e.g., navigate to the home screen)
        } catch (e: Exception) {
            // Handle exceptions (e.g., database errors)
        } finally {
            db.close()
        }
    }




}
