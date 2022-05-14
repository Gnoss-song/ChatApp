package com.example.chatapp.util

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import com.example.chatapp.R
import com.example.chatapp.api.Jsons
import com.example.chatapp.model.ItemChat
import com.example.chatapp.model.ItemChatList
import com.example.chatapp.model.entity.ErrorResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class Utils {
    //diffutil을 이용해 adapter에 들어오는 아이템을 비교합니다.
    val diffCallback = object : DiffUtil.ItemCallback<ItemChatList>() {
        override fun areItemsTheSame(
            oldItem: ItemChatList,
            newItem: ItemChatList
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ItemChatList,
            newItem: ItemChatList
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    val chatDiffCallback = object : DiffUtil.ItemCallback<ItemChat>() {
        override fun areItemsTheSame(
            oldItem: ItemChat,
            newItem: ItemChat
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ItemChat,
            newItem: ItemChat
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }
}

//코루틴 스코프를 생성합니다.
fun Fragment.createCoroutine(action: (CoroutineScope) -> Unit) {
    lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            action(this)
        }
    }
}

fun Fragment.findParentNavController(): NavController =
    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

inline fun ViewModel.onMain(
    crossinline body: suspend CoroutineScope.() -> Unit
) = viewModelScope.launch {
    body(this)
}

fun Fragment.toast(id: Int) {
    Toast.makeText(context, context?.getString(id), Toast.LENGTH_SHORT).show()
}

fun setErrorMessage(errorBody: ResponseBody?): ErrorResponse {
    return Jsons.gson().fromJson(errorBody?.string(), ErrorResponse::class.java)
}