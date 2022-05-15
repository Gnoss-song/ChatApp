package com.example.chatapp.model.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Member(
    val id: Long = 0,
    val username: String = "",
    val avatarUrl: String = "",
) : Parcelable
