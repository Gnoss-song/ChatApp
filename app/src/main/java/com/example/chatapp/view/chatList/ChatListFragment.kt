package com.example.chatapp.view.chatList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.R
import com.example.chatapp.databinding.FragmentChatListBinding
import com.example.chatapp.model.ItemChatList
import com.example.chatapp.util.Constants.BUNDLE_CHAT_INFO
import com.example.chatapp.util.createCoroutine
import com.example.chatapp.util.findParentNavController
import com.example.chatapp.view.adapter.ChatListAdapter
import com.example.chatapp.viewModel.ChatViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ChatListFragment : Fragment() {

    private lateinit var binding: FragmentChatListBinding
    private lateinit var chatAdapter: ChatListAdapter
    private val viewModel: ChatViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
//        observe()
    }

    //리사이클러뷰 초기화
    private fun initRecyclerView() {
        chatAdapter = ChatListAdapter()
        binding.chatListRecyclerView.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        addClickListener()
    }

    //아이템 클릭시 채팅방으로 이동
    private fun addClickListener() {
        chatAdapter.setItemClickListener(object : ChatListAdapter.ItemClickListener {
            override fun onClick(obj: ItemChatList) {
                moveToChatRoom(obj)
            }
        })
    }

    private fun moveToChatRoom(obj: ItemChatList) {
        val bundle = bundleOf(BUNDLE_CHAT_INFO to obj)
        findParentNavController().navigate(R.id.action_baseFragment_to_chatRoomFragment, bundle)
    }

    private fun observe() {
        createCoroutine {
            it.launch {
                observeChatList()
            }
        }
    }

    private suspend fun observeChatList() {
        //채팅방 리스트를 위해서 userId를 넣습니다.
        val userId = 100
        viewModel.loadChatList(userId).collectLatest {
            (binding.chatListRecyclerView.adapter as ChatListAdapter).submitData(it)
        }
    }
}