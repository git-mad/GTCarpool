package com.example.gtcarpool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        setContentView(R.layout.bottom_nav) //  testing nav screen
        val carpoolFragment = Carpoolfragment()
        val contactsFragment = ContactsFragment()
        val messagesFragment = MessagesFragment()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val profileFragment = ProfileFragment()
        setCurrentFragment(carpoolFragment)
        bottomNavigationView.setOnNavigationItemSelectedListener {

            when(it.itemId){
                R.id.home->setCurrentFragment(carpoolFragment)
                R.id.carpool->setCurrentFragment(carpoolFragment)
                R.id.settings->setCurrentFragment(profileFragment)
                R.id.messages->setCurrentFragment(contactsFragment)
            }
            true
        }
    }
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}