package com.example.gtcarpool

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

//https://www.youtube.com/watch?v=5mdV1hLbXzo
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var adapter: MyAdapter
lateinit var recyclerView: RecyclerView
private lateinit var requestsArrayList: ArrayList<Request>
lateinit var imageId: Array<Int>
lateinit var name : Array<String>
lateinit var date : Array<String>
lateinit var destination : Array<String>
lateinit var pickupLocation : Array<String>
lateinit var description : Array<String>





/**
 * A simple [Fragment] subclass.
 * Use the [Carpoolfragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Carpoolfragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_carpoolfragment, container, false)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Carpoolfragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Carpoolfragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the array list
        requestsArrayList = ArrayList()

        // Set up the RecyclerView
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        // Initialize the adapter with an empty list initially
        adapter = MyAdapter(requestsArrayList, context)
        recyclerView.adapter = adapter

        // Fetch data from Firestore
        dataInitialize()

        // Add a request button
        val addRequestButton = view.findViewById<ImageButton>(R.id.imageButton)
        addRequestButton.setOnClickListener {
            val intent = Intent(activity, NewRequest::class.java)
            startActivity(intent)
        }
    }


    private fun dataInitialize() {
        // Initialize the array list
        requestsArrayList = arrayListOf()

        // Get Firestore instance
        val db = FirebaseFirestore.getInstance()

        // Query Firestore to get all requests
        db.collection("requests")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    // Parse each document into a Request object
                    val request = document.toObject(Request::class.java)

                    // Add to the requestsArrayList
                    requestsArrayList.add(request)
                }
                // Notify adapter that the data has changed so it can update the RecyclerView
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

}