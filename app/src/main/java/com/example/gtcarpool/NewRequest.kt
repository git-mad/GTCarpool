package com.example.gtcarpool

import android.os.Bundle
import android.widget.Button
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class NewRequest : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_new_request)

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance()

        val nameInput = findViewById<TextInputEditText>(R.id.nameInput)
        val destinationInput = findViewById<TextInputEditText>(R.id.destinationInput)
        val pickupInput = findViewById<TextInputEditText>(R.id.pickupInput)
        val descriptionInput = findViewById<TextInputEditText>(R.id.descriptionInput)
        val submitButton = findViewById<Button>(R.id.button)

        submitButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val destination = destinationInput.text.toString().trim()
            val pickup = pickupInput.text.toString().trim()
            val description = descriptionInput.text.toString().trim()
            val date = Date()

            // Validate inputs
            if (name.isEmpty() || destination.isEmpty() || pickup.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Create a new request object
                val request = hashMapOf(
                    "name" to name,
                    "destination" to destination,
                    "pickup" to pickup,
                    "description" to description,
                    "date" to date
                )

                // Store the request in Firestore
                db.collection("requests")
                    .add(request)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(this, "Request submitted!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}
