package com.example.gtcarpool
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ProfileViewActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profileview)  // This sets the layout to profileview.xml
            // Find the Edit Profile button
            val editProfileButton = findViewById<Button>(R.id.EditProfileButton)

            // Set an OnClickListener to navigate to the ProfileFragment (Edit Profile)
            editProfileButton.setOnClickListener {
                // Navigate back to the Profile edit page (ProfileFragment)
                // Assuming you want to start an activity that contains the ProfileFragment
                val intent = Intent(this, MainActivity::class.java)
                // Optionally pass some data to indicate you're navigating to the edit page
                intent.putExtra("navigateToProfileEdit", true)
                startActivity(intent)
            }
        }
}