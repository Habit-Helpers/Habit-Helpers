package com.example.habit_helper

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.util.UUID

class ForgotPasswordFragment : Fragment() {

    private lateinit var dbHelper: MyDBHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = MyDBHelper(requireContext())

        val resetPasswordButton = view.findViewById<Button>(R.id.resetPasswordButton)
        val emailEditText = view.findViewById<TextView>(R.id.emailEditText)
        val newPasswordEditText = view.findViewById<TextView>(R.id.newPasswordEditText)



        resetPasswordButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val newPassword = newPasswordEditText.text.toString().trim() // Assuming you have an EditText for the new password

            if (email.isEmpty() || newPassword.isEmpty()) {
                // Handle empty fields
                return@setOnClickListener
            }

            val token = UUID.randomUUID().toString()

            dbHelper.storeResetToken(email, token)
            sendPasswordResetEmail(email, token)

            Toast.makeText(requireContext(), "Password reset email sent", Toast.LENGTH_SHORT).show()

            // Call handlePasswordReset after sending the password reset email
            handlePasswordReset(email, token, newPassword)
        }

    }

    private fun sendPasswordResetEmail(email: String, token: String) {
        val subject = "Password Reset Request"
        val body = "Hello,\n\nWe received a request to reset the password for your account. " +
                "To reset your password, please click on the following link:\n\n" +
                "http://example.com/reset-password?token=$token\n\n" +
                "If you did not request a password reset, you can ignore this email.\n\n" +
                "Best regards,\nYour App Team"
        Log.d("PasswordReset", "Sending password reset email to $email with token $token")
        Log.d("PasswordReset", "Subject: $subject")
        Log.d("PasswordReset", "Body: $body")
    }

    private fun verifyResetToken(email: String, token: String): Boolean {
        val storedToken = dbHelper.getResetToken(email)
        Log.d("VerifyResetToken", "Stored token: $storedToken, Provided token: $token")
        return storedToken == token
    }


    private fun handlePasswordReset(email: String, token: String, newPassword: String) {
        Log.d("PasswordReset", "Handling password reset for email: $email, token: $token, newPassword: $newPassword")

        val dbHelper = MyDBHelper(requireContext())
        if (verifyResetToken(email, token)) {
            // Token is valid, proceed with resetting the password
            if (dbHelper.updatePassword(email, newPassword)) {
                Log.d("PasswordReset", "Password reset successfully!")

                // Password reset successfully
                Toast.makeText(requireContext(), "Password reset successfully!", Toast.LENGTH_SHORT).show()
                // Navigate back to the login screen
                findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
            } else {
                // Failed to reset password
                Log.d("PasswordReset", "Failed to reset password")

                Toast.makeText(requireContext(), "Failed to reset password. Please try again.", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Invalid or expired token
            Log.d("PasswordReset", "Invalid or expired password reset link")

            Toast.makeText(requireContext(), "Invalid or expired password reset link. Please request a new one.", Toast.LENGTH_SHORT).show()
        }
    }

}
