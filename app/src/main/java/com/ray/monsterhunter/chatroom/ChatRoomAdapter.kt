package com.ray.monsterhunter.chatroom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ray.monsterhunter.data.ChatRoom
import com.ray.monsterhunter.databinding.ItemChatroomBinding
import com.ray.monsterhunter.util.TimeUtil
import com.ray.monsterhunter.util.Util
import java.util.*

class ChatRoomAdapter(private val onClickListener: OnClickListener ) :
    ListAdapter<ChatRoom, RecyclerView.ViewHolder>(DiffCallback) {

    class OnClickListener(val clickListener: (chatRoom : ChatRoom) -> Unit) {
        fun onClick(chatRoom: ChatRoom) = clickListener(chatRoom)
    }

    class ChatRoomViewHolder(private var binding: ItemChatroomBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(chatRoom: ChatRoom, onClickListener: OnClickListener) {

            var AllStampTimeToDate = chatRoom.createTime?.let { TimeUtil.AllStampToDate(it, Locale.TAIWAN) }

            binding.chatRoomListStartTime.text = AllStampTimeToDate
            binding.even = chatRoom
            binding.root.setOnClickListener { onClickListener.onClick(chatRoom) }
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ChatRoom>() {
        override fun areItemsTheSame(oldItem: ChatRoom, newItem: ChatRoom): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: ChatRoom, newItem: ChatRoom): Boolean {
            return oldItem.image == newItem.image
        }

        private const val ITEM_VIEW_TYPE_ARTICLE = 0x00
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_ARTICLE -> ChatRoomViewHolder(ItemChatroomBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is ChatRoomViewHolder -> {
                holder.bind((getItem(position) as ChatRoom), onClickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return ITEM_VIEW_TYPE_ARTICLE
    }
}
