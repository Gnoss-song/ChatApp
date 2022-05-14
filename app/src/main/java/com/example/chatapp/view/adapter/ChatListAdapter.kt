package com.example.chatapp.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.chatapp.R
import com.example.chatapp.databinding.ItemChatListBinding
import com.example.chatapp.model.ItemChatList
import com.example.chatapp.util.Utils
import jp.wasabeef.glide.transformations.MaskTransformation

interface ChatListAdapterInterface {
    fun setItem(binding: ItemChatListBinding, obj: ItemChatList)
}


class ChatListAdapter :
    PagingDataAdapter<ItemChatList, RecyclerView.ViewHolder>(Utils().diffCallback),
    ChatListAdapterInterface {

    //클릭 인터페이스 정의
    interface ItemClickListener {
        fun onClick(obj: ItemChatList)
    }

    //클릭리스너 선언
    private var itemClickListener: ItemClickListener? = null

    //클릭리스너 등록 매소드
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemChatListBinding = ItemChatListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ChatListViewHolder(itemChatListBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { (holder as ChatListViewHolder).bind(it) }
    }

    inner class ChatListViewHolder(private val itemChatListBinding: ItemChatListBinding) :
        RecyclerView.ViewHolder(itemChatListBinding.root) {
        private val glide = Glide.with(itemView.context)

        @SuppressLint("CheckResult")
        fun bind(obj: ItemChatList) {
            glide.load(obj.userProfile).placeholder(R.drawable.placeholder_profile).apply {
                RequestOptions.bitmapTransform(
                    MultiTransformation(
                        CenterCrop(),
                        MaskTransformation(R.drawable.background_profile)
                    )
                )
            }.into(itemChatListBinding.userProfile)
            setItem(itemChatListBinding, obj)
            itemView.setOnClickListener {
                itemClickListener?.onClick(obj)
            }
        }
    }

    //주어진 아이템 세팅
    override fun setItem(binding: ItemChatListBinding, obj: ItemChatList) {
        binding.userName.text = obj.userName
        binding.lastDate.text = obj.lastDate
        binding.userPosition.text = obj.userPosition
        binding.userChatPreview.text = obj.chatPreView
        binding.chatBadge.isVisible = obj.badge > 0
        binding.chatBadge.text = if (obj.badge > 999) {
            "999+"
        } else {
            obj.badge.toString()
        }
    }
}
