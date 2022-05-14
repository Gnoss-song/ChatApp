package com.example.chatapp.view.chatList.chatRoom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.R
import com.example.chatapp.databinding.FragmentChatRoomBinding
import com.example.chatapp.model.ItemChatList
import com.example.chatapp.util.Constants.BUNDLE_CHAT_INFO
import com.example.chatapp.util.createCoroutine
import com.example.chatapp.util.toast
import com.example.chatapp.view.adapter.ChatRoomAdapter
import com.example.chatapp.viewModel.ChatViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ChatRoomFragment : Fragment() {
    lateinit var binding: FragmentChatRoomBinding
    private lateinit var chatRoomAdapter: ChatRoomAdapter
    lateinit var item: ItemChatList
    private val viewModel: ChatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        item = arguments?.getParcelable<ItemChatList>(BUNDLE_CHAT_INFO) as ItemChatList
        initRecyclerView()
        btnBack()
        btnRegister()
        observe()
    }

    //리사이클러뷰 초기화
    private fun initRecyclerView() {
        chatRoomAdapter = ChatRoomAdapter()
        binding.chatRecyclerView.apply {
            adapter = chatRoomAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
    private fun observe(){
        createCoroutine {
            it.launch {
                observeChat()
            }
            it.launch {
                observeRegister()
            }
        }
    }

    private suspend fun observeChat(){
        viewModel.loadChat(item.id).collectLatest {
            (binding.chatRecyclerView.adapter as ChatRoomAdapter).submitData(it)
        }
    }

    private suspend fun observeRegister(){
        viewModel.register.collect {
            if(it){
                toast(R.string.toastRegisterChat)
            }
        }
    }

    private fun btnBack(){
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun btnRegister(){
        binding.btnRegister.setOnClickListener {
            viewModel.register(binding.commentEditText.text.toString())
        }
    }
}