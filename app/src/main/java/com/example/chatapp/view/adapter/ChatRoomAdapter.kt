package com.example.chatapp.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.chatapp.R
import com.example.chatapp.databinding.ItemChatRoomLeftBinding
import com.example.chatapp.databinding.ItemChatRoomRightBinding
import com.example.chatapp.model.ItemChat
import com.example.chatapp.util.Constants.CHAT_MINE
import com.example.chatapp.util.Constants.CHAT_OTHER
import com.example.chatapp.util.Utils
import jp.wasabeef.glide.transformations.MaskTransformation


class ChatRoomAdapter :
    PagingDataAdapter<ItemChat, RecyclerView.ViewHolder>(Utils().chatDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == CHAT_MINE) {
            val itemRightBinding = ItemChatRoomRightBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            ChatRightViewHolder(itemRightBinding)
        } else {
            val itemLeftBinding = ItemChatRoomLeftBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            ChatLeftViewHolder(itemLeftBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { (holder as BaseViewHolder).bind(it) }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position)?.other != true) {
            //나의 채팅
            CHAT_MINE
        } else {
            //상대방의 채팅
            CHAT_OTHER
        }
    }

    abstract inner class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(obj: ItemChat)
    }

    inner class ChatRightViewHolder(private val binding: ItemChatRoomRightBinding) :
        BaseViewHolder(binding.root) {
        private val glide = Glide.with(itemView.context)

        @SuppressLint("CheckResult")
        override fun bind(obj: ItemChat) {
            glide.load(obj.userProfile).placeholder(R.drawable.placeholder_profile).apply {
                RequestOptions.bitmapTransform(
                    MultiTransformation(
                        CenterCrop(),
                        MaskTransformation(R.drawable.background_profile)
                    )
                )
            }.into(binding.userProfile)
            setMyItem(binding, obj)
        }
    }

    inner class ChatLeftViewHolder(private val binding: ItemChatRoomLeftBinding) :
        BaseViewHolder(binding.root) {
        private val glide = Glide.with(itemView.context)

        @SuppressLint("CheckResult")
        override fun bind(obj: ItemChat) {
            glide.load(obj.userProfile).placeholder(R.drawable.placeholder_profile).apply {
                RequestOptions.bitmapTransform(
                    MultiTransformation(
                        CenterCrop(),
                        MaskTransformation(R.drawable.background_profile)
                    )
                )
            }.into(binding.userProfile)
            setOtherItem(binding, obj)
        }
    }

    fun setMyItem(binding: ItemChatRoomRightBinding, obj: ItemChat) {
        binding.userName.text = obj.userName
        binding.userChat.text = obj.chat
        binding.lastDate.text = obj.lastDate
    }

    fun setOtherItem(binding: ItemChatRoomLeftBinding, obj: ItemChat) {
        binding.userName.text = obj.userName
        binding.userChat.text = obj.chat
        binding.lastDate.text = obj.lastDate
    }
}
