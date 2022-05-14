package com.example.chatapp.view.chatList.chatRoom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.databinding.FragmentChatRoomBinding
import com.example.chatapp.view.adapter.ChatRoomAdapter

class ChatRoomFragment : Fragment() {
    lateinit var binding: FragmentChatRoomBinding
    private lateinit var chatRoomAdapter: ChatRoomAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    //리사이클러뷰 초기화
    private fun initRecyclerView() {
        chatRoomAdapter = ChatRoomAdapter()
        binding.chatRecyclerView.apply {
            adapter = chatRoomAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}