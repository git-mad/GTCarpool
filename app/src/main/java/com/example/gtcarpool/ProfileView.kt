package com.example.gtcarpool

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button


class ProfileView: AppCompatActivity () {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profileview)  // This sets the layout to profile.xml

        // Find the button in profile.xml
        val switchButton: Button = findViewById(R.id.EditProfileButton)

        // Set an onClickListener on the button to switch to ProfileViewActivity
        switchButton.setOnClickListener {
            // Create an Intent to start ProfileViewActivity
            val intent = Intent(this, ProfileViewActivity::class.java)
            startActivity(intent)
        }
    }
}
