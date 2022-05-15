package com.example.chatapp.di

import android.app.Application
import android.content.Context
import com.example.chatapp.util.Constants.BASE_URL
import com.example.chatapp.api.Jsons
import com.example.chatapp.api.ApiService
import com.example.chatapp.api.AuthInterceptor
import com.example.chatapp.util.Constants
import com.example.chatapp.util.UserManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }

    @Singleton
    @Provides
    fun provideUserManager(
        application: Application
    ): UserManager {
        return UserManager(
            application.getSharedPreferences(
                Constants.UserManager,
                Context.MODE_PRIVATE
            )
        )
    }

    @Singleton
    @Provides
    fun provideHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        client.addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
        return client.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Jsons.gson()))
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
