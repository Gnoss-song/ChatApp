package com.example.chatapp.model.repository

import androidx.paging.PagingData
import com.example.chatapp.model.entity.Chat
import kotlinx.coroutines.flow.Flow

interface ChatRepositoryInterface {
    fun getChatList(id: Int): Flow<PagingData<Chat>>
    fun getChatRoomList(id: Int): Flow<PagingData<Chat>>
    fun registerChat(chat: String): Flow<Unit>
}
