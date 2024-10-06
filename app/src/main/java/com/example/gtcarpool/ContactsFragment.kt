package com.example.gtcarpool

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * A simple [Fragment] subclass.
 * Use the [ContactsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactsFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createContactRecyclerView()
    }

    private fun createContactRecyclerView() {
        val dataset = arrayOf("John Doe", "Jane Doe", "James Madison", "Benjamin Franklin")

        val customAdapter = CustomAdapter(dataset) { contactName ->
            Log.d("ContactsFragment", "Clicked on $contactName")
            val messagesFragment = MessagesFragment.newInstance(contactName)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.flFragment, messagesFragment)
                .addToBackStack(null)  // This allows back navigation
                .commit()
            Log.d("ContactsFragment", "Switched to MessagesFragment")
        }


        val recyclerView: RecyclerView? = view?.findViewById(R.id.fragment_contacts_recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        recyclerView?.adapter = customAdapter
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        // Factory method to create a new instance of this fragment
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    class CustomAdapter(
        private val dataSet: Array<String>,
        private val onClick: (String) -> Unit  // Function to handle click
    ) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textView: TextView = view.findViewById(R.id.contactsView)
            val imageView: ImageView = view.findViewById(R.id.profile_image)
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.list_item_contact, viewGroup, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            val contactName = dataSet[position]
            viewHolder.textView.text = contactName
            viewHolder.imageView.setImageResource(R.drawable.empty_profile)

            // Set the click listener for each item, passing the contact name to the onClick function
            viewHolder.itemView.setOnClickListener {
                Log.d("CustomAdapter", "Clicked on $contactName")
                onClick(contactName)
            }

        }

        override fun getItemCount() = dataSet.size
    }
}
