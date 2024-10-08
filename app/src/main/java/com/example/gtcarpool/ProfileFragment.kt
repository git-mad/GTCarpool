package com.example.gtcarpool

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var logoutButton: AppCompatImageButton
    private lateinit var saveButton: Button
    private lateinit var nameEditText: EditText
    private lateinit var phoneEditText: EditText
    //private lateinit var emailEditTexts: TextView
    private lateinit var gtidEditText: EditText
    private lateinit var emailTextView: TextView
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        logoutButton = view.findViewById(R.id.logoutButton)

        // Set an onClickListener to log out
        logoutButton.setOnClickListener {
            // Sign the user out
            auth.signOut()

            // Redirect to WelcomeActivity
            val intent = Intent(requireContext(), WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        saveButton = view.findViewById(R.id.savebutton)
        nameEditText = view.findViewById(R.id.nameTextEdit)
        phoneEditText = view.findViewById(R.id.editTextPhone)
        //emailEditTexts = view.findViewById(R.id.emailEditText)
        gtidEditText = view.findViewById(R.id.gtidTextEdit)
        emailTextView = view.findViewById(R.id.emailTextView)
        val auth = FirebaseAuth.getInstance()
        emailTextView.text = auth.currentUser?.email
        val uid = auth.currentUser?.uid

        saveButton.setOnClickListener {
            val user = hashMapOf(
                "Name" to nameEditText.text.toString(),
                "Phone" to phoneEditText.text.toString(),
                "GTID" to gtidEditText.text.toString(),
                "Email" to auth.currentUser?.email
            )
            if (uid != null) {
                db.collection("users").document(uid).set(user)
            }
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}