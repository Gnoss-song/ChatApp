package com.example.chatapp.model.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.chatapp.api.ApiService
import com.example.chatapp.model.entity.Chat
import com.example.chatapp.util.Constants.PAGING_SIZE


class ChatPagingSource(private val service: ApiService, private val id: Int) :
    ChatPagingSourceInterface, PagingSource<String, Chat>() {
    private var nextKey: String? = null
    override suspend fun load(params: LoadParams<String>): LoadResult<String, Chat> {
        return try {
            val response = service.getChat(id, PAGING_SIZE)
            val data: List<Chat> = response.body()?.content!!
            LoadResult.Page(data = data, prevKey = null, nextKey = nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Chat>): String {
        return nextKey!!
    }
}