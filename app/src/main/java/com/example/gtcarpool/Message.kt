data class Message(
    val content: String = "",
    val message_num: Long = 0,
    val receiver_name: String = "",
    val receiver_uid: String = "",
    val sender_name: String = "",
    val sender_uid: String = "",
    val time: Long = System.currentTimeMillis()  // Add this line for timestamp
)
