package com.example.gtcarpool

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MessagesFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var contactName: String? = null
    private lateinit var messagesRef: DatabaseReference
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var customAdapter: CustomAdapter
    private val messagesDataset = mutableListOf<Message>() // Store messages here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            contactName = it.getString(ARG_CONTACT_NAME)
        }
        auth = FirebaseAuth.getInstance()
        messagesRef = FirebaseDatabase.getInstance().getReference("message_list")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chatTitle = view.findViewById<TextView>(R.id.chat_title)
        chatTitle.text = "Chat with $contactName"

        createContactRecyclerView(view)

        messageEditText = view.findViewById(R.id.typeMessage)
        sendButton = view.findViewById(R.id.sendButton)

        sendButton.setOnClickListener {
            val message = messageEditText.text.toString().trim()
            if (message.isNotEmpty()) {
                sendMessage()
                messageEditText.text.clear()
            } else {
                Log.d("MessagesFragment", "Empty message, not sending.")
            }
        }
        fetchMessages()
    }

    private fun sendMessage() {
        val messageText = messageEditText.text.toString().trim()

        if (messageText.isNotEmpty()) {
            val senderUid = auth.currentUser?.uid ?: return
            val senderName = auth.currentUser?.displayName ?: "Anonymous"
            val receiverUid = "receiverUid"
            val receiverName = contactName ?: "Unknown"
            val messageNum = System.currentTimeMillis()

            val newMessage = Message(
                message_num = messageNum,
                sender_uid = senderUid,
                sender_name = senderName,
                receiver_uid = receiverUid,
                receiver_name = receiverName,
                content = messageText,
                time = System.currentTimeMillis()
            )

            // Send the message to Firebase
            messagesRef.push().setValue(newMessage).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    customAdapter.addMessage(newMessage)
                    messageEditText.text.clear()
                } else {
                    Log.e("MessagesFragment", "Failed to send message: ${task.exception?.message}")
                }
            }
        }
    }


    private fun createContactRecyclerView(view: View) {
        customAdapter = CustomAdapter(messagesDataset)
        val recyclerView: RecyclerView = view.findViewById(R.id.fragment_messages_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = customAdapter
    }

    private fun fetchMessages() {
        messagesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // messagesDataset.clear()
                for (snapshot in dataSnapshot.children) {
                    val message = snapshot.getValue(Message::class.java)
                    message?.let {
                        Log.d("MessagesFragment", "Fetched message: ${it.content}") // Log fetched message
                        messagesDataset.add(it) // Add the message to the dataset
                    }
                }
                customAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("MessagesFragment", "Failed to read messages: ${databaseError.message}")
            }
        })
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

    class CustomAdapter(private val dataSet: MutableList<Message>) :
        RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textView: TextView = view.findViewById(R.id.chat_message)
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.list_item_message, viewGroup, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            val message = dataSet[position]
            viewHolder.textView.text = message.content

            // Adjust alignment and background based
            val layoutParams = viewHolder.textView.layoutParams as LinearLayout.LayoutParams

            if (message.sender_uid == FirebaseAuth.getInstance().currentUser?.uid) {
                layoutParams.gravity = Gravity.END
                viewHolder.textView.setBackgroundColor(Color.parseColor("#FFFFFF"))
            } else {
                layoutParams.gravity = Gravity.START
                viewHolder.textView.setBackgroundColor(Color.parseColor("#F8EED2"))
            }

            viewHolder.textView.layoutParams = layoutParams
        }

        override fun getItemCount() = dataSet.size

        fun addMessage(message: Message) {
            dataSet.add(message)
            notifyItemInserted(dataSet.size)

        }
    }
}
