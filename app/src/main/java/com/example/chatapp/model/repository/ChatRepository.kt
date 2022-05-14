package com.example.chatapp.model.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.chatapp.api.ApiService
import com.example.chatapp.model.entity.Chat
import com.example.chatapp.model.entity.ErrorException
import com.example.chatapp.util.Constants.PAGING_SIZE
import com.example.chatapp.util.setErrorMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(
    private val service: ApiService
) : ChatRepositoryInterface {

    override fun getChatList(id: Int): Flow<PagingData<Chat>> {
        return Pager(PagingConfig(PAGING_SIZE)) {
            ChatPagingSource(service, id)
        }.flow
    }

    override fun getChatRoomList(id: Int): Flow<PagingData<Chat>> {
        return Pager(PagingConfig(PAGING_SIZE)) {
            ChatPagingSource(service, id)
        }.flow
    }

    override fun registerChat(chat: String): Flow<Unit> {
        val registerChat: Flow<Unit> = flow {
            val registerChat = service.registerChat(chat)
            if (registerChat.isSuccessful) {
                registerChat.body()?.let {
                    emit(it)
                }
            } else {
                throw ErrorException(setErrorMessage(registerChat.errorBody()))
            }
        }
        return registerChat
    }
}