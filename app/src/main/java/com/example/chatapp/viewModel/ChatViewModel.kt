package com.example.chatapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.chatapp.model.ItemChat
import com.example.chatapp.model.ItemChatList
import com.example.chatapp.model.entity.ErrorException
import com.example.chatapp.model.repository.ChatRepository
import com.example.chatapp.util.onMain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: ChatRepository
) : ViewModel() {

    private val _register: MutableStateFlow<Boolean> by lazy {
        MutableStateFlow(false)
    }
    val register = _register.asSharedFlow()

    fun register(chat: String) {
        onMain {
            repository.registerChat(chat).catch {
                if (it is ErrorException) {
                    Timber.e("${it.errorResponse.errorDescription},${it.errorResponse.error}")
                } else {
                    Timber.e("${it.message}")
                }
            }.collect {
                _register.emit(true)
            }
        }
    }

    fun loadChatList(id: Int): Flow<PagingData<ItemChatList>> {
        val newResult = repository.getChatList(id).catch { Timber.d(it) }.map { pagingData ->
            pagingData.map {
                ItemChatList(
                    it.id,
                    it.userName,
                    it.userProfile,
                    it.lastDate,
                    it.userPosition,
                    it.chatPreView,
                    it.badge
                )
            }
        }.cachedIn(viewModelScope)
        return newResult
    }

    fun loadChat(id: Int): Flow<PagingData<ItemChat>> {
        val newResult = repository.getChatRoomList(id).catch { Timber.d(it) }.map { pagingData ->
            pagingData.map {
                ItemChat(
                    it.id,
                    it.other,
                    it.userName,
                    it.userProfile,
                    it.lastDate,
                    it.chat
                )
            }
        }.cachedIn(viewModelScope)
        return newResult
    }
}