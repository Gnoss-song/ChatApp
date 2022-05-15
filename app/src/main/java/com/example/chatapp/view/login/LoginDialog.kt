package com.example.chatapp.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.chatapp.R
import com.example.chatapp.databinding.DialogLoginBinding
import com.example.chatapp.model.entity.Member
import com.example.chatapp.util.UserManager
import com.example.chatapp.util.createCoroutine
import com.example.chatapp.util.toast
import com.example.chatapp.view.base.BaseFullBottomSheetDialogFragment
import com.example.chatapp.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


@AndroidEntryPoint
class LoginDialog : BaseFullBottomSheetDialogFragment() {

    @Inject
    lateinit var userManager: UserManager
    private val viewModel: LoginViewModel by viewModels()
    lateinit var binding: DialogLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addClickListener()
        observe()
    }

    private fun addClickListener() {
        binding.btnLogin.setOnClickListener {
            login()
        }
        binding.btnClose.setOnClickListener {
            dismiss()
        }
    }

    private fun observe() {
        createCoroutine {
            it.launch {
                observeLoginStatus()
            }
        }
    }

    private suspend fun observeLoginStatus() {
        viewModel.loginStatus.collect {
            if (it != null) {
                toast(R.string.toastLogin)
                userManager.login()
                setMember(it)
            }
        }
    }

    private fun login() {
        val id = binding.editTextId.text.toString()
        val password = binding.editTextPassword.text.toString()

        if (id.isNotBlank() && password.isNotBlank()) {
            viewModel.login(
                binding.editTextId.text.toString(),
                binding.editTextPassword.text.toString()
            )
        } else {
            toast(R.string.toastLoginInfo)
        }
    }

    private fun setMember(member: Member) {
        userManager.setMemberInfo(member)
        dismiss()
    }
}
