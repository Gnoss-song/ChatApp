package com.example.chatapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemChatList(
    val id: Int,
    val userName: String,
    val userProfile: String,
    val lastDate: String,
    val userPosition: String,
    val chatPreView: String,
    val badge: Int
):Parcelable

@Parcelize
data class ItemChat(
    val id: Int,
    val other: Boolean,
    val userName: String,
    val userProfile: String,
    val lastDate: String,
    val chat: String
):Parcelable
