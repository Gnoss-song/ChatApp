package com.example.chatapp.api

import com.example.chatapp.model.entity.ChatResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("chat")
    suspend fun getChat(
        @Query("id") id: Int,
        @Query("size") size: Int = 20
    ): Response<ChatResponse>

    @PUT("chat")
    suspend fun registerChat(
        @Body chat: String
    ): Response<Unit>

}
