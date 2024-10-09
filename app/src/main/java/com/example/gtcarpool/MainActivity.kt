package com.example.gtcarpool


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        // Check if the user is logged in
        if (auth.currentUser == null) {
            // User is not logged in, redirect to LoginActivity
            val welcomeIntent = Intent(this, WelcomeActivity::class.java)
            startActivity(welcomeIntent)
            finish() // Prevent going back to MainActivity without logging in
        } else {
            // User is signed in, proceed with the bottom navigation
            setContentView(R.layout.bottom_nav)
            val carpoolFragment = Carpoolfragment()
            val contactsFragment = ContactsFragment()
            val profileFragment = ProfileFragment()
            val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            setCurrentFragment(carpoolFragment)

            bottomNavigationView.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> setCurrentFragment(carpoolFragment)
                    R.id.carpool -> setCurrentFragment(carpoolFragment)
                    R.id.settings -> setCurrentFragment(profileFragment)
                    R.id.messages -> setCurrentFragment(contactsFragment)

                }
                true
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
}
