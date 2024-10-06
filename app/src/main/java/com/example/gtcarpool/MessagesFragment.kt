package com.example.gtcarpool

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MessagesFragment : Fragment() {

    private var contactName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            contactName = it.getString(ARG_CONTACT_NAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load the chat messages for the contact
        val chatTitle = view.findViewById<TextView>(R.id.chat_title)
        chatTitle.text = "Chat with $contactName"

        // Set up RecyclerView for chat messages
        createContactRecyclerView(view)
    }

    private fun createContactRecyclerView(view: View) {
        val dataset = arrayOf("Message Response 1", "Message Reply 1", "Message Response 2", "Message Reply 2")
        val customAdapter = CustomAdapter(dataset)

        val recyclerView: RecyclerView = view.findViewById(R.id.fragment_messages_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = customAdapter
    }

    companion object {
        private const val ARG_CONTACT_NAME = "contact_name"

        fun newInstance(contactName: String) =
            MessagesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_CONTACT_NAME, contactName)
                }
            }
    }

    class CustomAdapter(private val dataSet: Array<String>) :
        RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textView: TextView = view.findViewById(R.id.chat_message)  // Make sure to use the correct ID
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.list_item_message, viewGroup, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            viewHolder.textView.text = dataSet[position]
        }

        override fun getItemCount() = dataSet.size
    }
}
