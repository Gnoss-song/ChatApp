package com.example.chatapp.viewModel

import androidx.lifecycle.ViewModel
import com.example.chatapp.model.entity.ErrorException
import com.example.chatapp.model.entity.Member
import com.example.chatapp.model.repository.LoginRepository
import com.example.chatapp.util.onMain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {

    private val _loginStatus: MutableStateFlow<Member?> by lazy {
        MutableStateFlow(null)
    }
    val loginStatus = _loginStatus.asSharedFlow()

    fun login(id: String, password: String) {
        onMain {
            _loginStatus.emit(Member(1, "STS", ""))
        }
//        onMain {
//            repository.login(id, password).catch {
//                if (it is ErrorException) {
//                    Timber.e("${it.errorResponse.errorDescription},${it.errorResponse.error}")
//                } else {
//                    Timber.e("${it.message}")
//                }
//            }.collect {
//                _loginStatus.emit(it)
//            }
//        }
    }
}
