package com.example.chatapp.api

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

object Jsons {
    //snake로 되어있을경우를 대비한 FieldNamingPolicy
    private val GSON: Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    fun gson(): Gson {
        return GSON
    }
}