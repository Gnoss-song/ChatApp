package com.example.chatapp.model.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.chatapp.model.entity.Chat

interface ChatPagingSourceInterface {
    suspend fun load(params: PagingSource.LoadParams<String>): PagingSource.LoadResult<String, Chat>
    fun getRefreshKey(state: PagingState<String, Chat>): String
}
