package com.example.chatapp.model.entity


class ChatResponse(
    val content: List<Chat> = emptyList(),
    var nextCursor: String? = null,
    val nextRef: String? = null
)

data class Chat(
    val id: Int,
    val other: Boolean,
    val userName: String,
    val userProfile: String,
    val lastDate: String,
    val userPosition: String,
    val chatPreView: String,
    val badge: Int,
    val chat: String
)

