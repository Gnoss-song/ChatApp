package com.example.chatapp.model.repository

import com.example.chatapp.api.ApiService
import com.example.chatapp.model.entity.ErrorException
import com.example.chatapp.model.entity.Member
import com.example.chatapp.util.setErrorMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(
    private val service: ApiService
) : LoginRepositoryInterface {

    override fun login(id : String,password : String): Flow<Member> {
        val login: Flow<Member> = flow {
            val login = service.login(id,password)
            if (login.isSuccessful) {
                login.body()?.let {
                    emit(it)
                }
            } else {
                throw ErrorException(setErrorMessage(login.errorBody()))
            }
        }
        return login
    }
}