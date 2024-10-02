package com.example.gtcarpool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.widget.AppCompatImageButton
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signUpButton: AppCompatImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()

        // Get references to UI elements
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
        signUpButton = findViewById(R.id.signUpButton)

        // Handle sign-up button click
        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            if (validateInputs(email, password, confirmPassword)) {
                signUpUser(email, password)
            }
        }
    }

    private fun validateInputs(email: String, password: String, confirmPassword: String): Boolean {
        if (email.isEmpty()) {
            emailEditText.error = "Email is required"
            emailEditText.requestFocus()
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Please enter a valid email"
            emailEditText.requestFocus()
            return false
        }

        if (password.isEmpty()) {
            passwordEditText.error = "Password is required"
            passwordEditText.requestFocus()
            return false
        }

        if (password.length < 6) {
            passwordEditText.error = "Password must be at least 6 characters long"
            passwordEditText.requestFocus()
            return false
        }

        if (password != confirmPassword) {
            confirmPasswordEditText.error = "Passwords do not match"
            confirmPasswordEditText.requestFocus()
            return false
        }

        return true
    }

    private fun signUpUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign up successful, redirect to MainActivity
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else {
                    // If sign-up fails, display a message to the user
                    Toast.makeText(this, "Registration failed. ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}