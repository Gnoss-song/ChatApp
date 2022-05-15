package com.example.chatapp.model.repository

import androidx.paging.PagingData
import com.example.chatapp.model.entity.Chat
import com.example.chatapp.model.entity.Member
import kotlinx.coroutines.flow.Flow

interface LoginRepositoryInterface {
    fun login(id: String,password: String): Flow<Member>
}
