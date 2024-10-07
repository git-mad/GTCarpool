package com.example.gtcarpool

data class Message(
    val message_num: Long,
    val sender_uid: String,
    val sender_name: String,
    val receiver_uid: String,
    val receiver_name: String,
    val content: String,
    val time: Long
)
