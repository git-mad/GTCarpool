package com.example.gtcarpool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Patterns
import androidx.appcompat.widget.AppCompatImageButton
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
class LoginActivity : AppCompatActivity() {
    //private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: AppCompatImageButton
    private lateinit var backButton: AppCompatImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        // Get references to UI elements
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        backButton = findViewById(R.id.backButton)

        // Handle login button click
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (validateInputs(email, password)) {
                loginUser(email, password)
            }
        }

        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }

    private fun validateInputs(email: String, password: String): Boolean {
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

        return true
    }

    private fun loginUser(email: String, password: String) {
       auth.signInWithEmailAndPassword(email, password)
            addOnCompleteListener(this) { task ->
               if (task.isSuccessful) {
                    // Login successful, redirect to MainActivity
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else {
                    // If login fails, display a message to the user
                    Toast.makeText(this, "Authentication failed. ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}