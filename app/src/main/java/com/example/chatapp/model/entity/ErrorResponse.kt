package com.example.chatapp.model.entity

class ErrorException(val errorResponse: ErrorResponse) : RuntimeException()

data class ErrorResponse(
    val error: String? = null,
    val errorDescription: String? = null
)
