
package com.example.gtcarpool

import Message
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
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class MessagesFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var contactName: String? = null
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var customAdapter: CustomAdapter
    private lateinit var messagesRef: CollectionReference
    private val messagesDataset = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            contactName = it.getString(ARG_CONTACT_NAME)
        }
        auth = FirebaseAuth.getInstance()
        val chatId = "chat_1" // Example_id
        messagesRef = FirebaseFirestore.getInstance().collection("messages").document(chatId).collection("messages")
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

            val timeStamp = System.currentTimeMillis()

            val newMessage = Message(
                content = messageText,
                message_num = messageNum,
                receiver_name = receiverName,
                receiver_uid = receiverUid,
                sender_name = senderName,
                sender_uid = senderUid,
                time = timeStamp
            )

            // Send the message to Firestore
            messagesRef.add(newMessage).addOnCompleteListener { task ->
                if (task.isSuccessful) {
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
        messagesRef.orderBy("message_num").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.e("MessagesFragment", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                messagesDataset.clear()
                for (document in snapshot.documents) {
                    val message = document.toObject(Message::class.java)
                    message?.let {
                        Log.d("MessagesFragment", "Fetched message: ${it.content}") // Log fetched message
                        messagesDataset.add(it) // Add the message to the dataset
                    }
                }
                customAdapter.notifyDataSetChanged()
            } else {
                Log.d("MessagesFragment", "No messages found.")
            }
        }
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
            val timeView: TextView = view.findViewById(R.id.chat_message_time)  // Add a TextView for time
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.list_item_message, viewGroup, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            val message = dataSet[position]
            viewHolder.textView.text = message.content

            // Convert timestamp to human-readable format
            val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
            val messageTime = dateFormat.format(Date(message.time))
            viewHolder.timeView.text = messageTime

            // Adjust alignment and background based on sender
            val layoutParams = viewHolder.textView.layoutParams as LinearLayout.LayoutParams

            if (message.sender_uid == FirebaseAuth.getInstance().currentUser?.uid) {
                layoutParams.gravity = Gravity.END // Align to the right for the current user
                viewHolder.textView.setBackgroundColor(Color.parseColor("#FFFFFF")) // Sender's message color
            } else {
                layoutParams.gravity = Gravity.START // Align to the left for other users
                viewHolder.textView.setBackgroundColor(Color.parseColor("#F8EED2")) // Receiver's message color
            }

            viewHolder.textView.layoutParams = layoutParams
        }

        override fun getItemCount() = dataSet.size
    }

}
