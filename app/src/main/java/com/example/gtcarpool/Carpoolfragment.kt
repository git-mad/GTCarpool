package com.example.gtcarpool

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


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
        dataInitialize()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = MyAdapter(requestsArrayList, context)
        recyclerView.adapter = adapter

        val addRequestButton = view.findViewById<ImageButton>(R.id.imageButton)
        addRequestButton.setOnClickListener {
            // Launch the NewRequest activity
            val intent = Intent(activity, NewRequest::class.java)
            startActivity(intent)
        }


    }

    private fun dataInitialize() {
        requestsArrayList = arrayListOf<Request>();
        imageId = arrayOf (
            R.drawable.a,
            R.drawable.a,
            R.drawable.a
        )
        name = arrayOf (
            "Eren Yeager",
            "Nitya",
            "Hi"
        )
        date = arrayOf (
            "9/30/24",
            "9/29/24",
            "9/28/24"
        )
        destination = arrayOf (
            "NAV",
            "West Village",
            "Culc"
        )
        pickupLocation = arrayOf (
            "Airport",
            "Airport",
            "Airport"
        )
        description = arrayOf (
                "hi",
        "hiii",
        "hiiiiiii"
        )

        for (i in imageId.indices) {
            val requestss = Request(date[i], destination[i], pickupLocation[i], description[i], imageId[i],  name[i]);
            requestsArrayList.add(requestss);
        }


    }
}