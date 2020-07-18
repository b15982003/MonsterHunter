package com.ray.monsterhunter.chattoomdetail


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ray.monsterhunter.data.Message
import com.ray.monsterhunter.databinding.ItemChatroomMessageBinding
import com.ray.monsterhunter.util.TimeUtil
import com.ray.monsterhunter.util.UserManager
import java.util.*

class ChatRoomDetailAdapter(private val onClickListener: OnClickListener ) :
    ListAdapter<Message, RecyclerView.ViewHolder>(DiffCallback) {

    class OnClickListener(val clickListener: (message: Message) -> Unit) {
        fun onClick(message: Message) = clickListener(message)
    }

    class ChatRoomDetailViewHolder(private var binding: ItemChatroomMessageBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message : Message, onClickListener: OnClickListener) {

            var AllStampTimeToDate = message.createTime?.let { TimeUtil.AllStampToDate(it, Locale.TAIWAN) }

            binding.chatRoomDetailItemCreateTime.text = AllStampTimeToDate
            binding.chatRoomDetailItemCreateTimeRight.text = AllStampTimeToDate

            if (UserManager.userData.email == message.email){


                binding.chatRoomDetailItemMessageRight.visibility = View.VISIBLE
                binding.chatRoomDetailItemCreateTimeRight.visibility = View.VISIBLE

                binding.chatRoomDetailItemImage.visibility = View.GONE
                binding.chatRoomDetailItemName.visibility = View.GONE
                binding.chatRoomDetailItemLine.visibility = View.GONE
                binding.chatRoomDetailItemMessage.visibility = View.GONE
                binding.chatRoomDetailItemCreateTime.visibility = View.GONE

            }else{


                binding.chatRoomDetailItemMessageRight.visibility = View.GONE
                binding.chatRoomDetailItemCreateTimeRight.visibility = View.GONE

                binding.chatRoomDetailItemImage.visibility = View.VISIBLE
                binding.chatRoomDetailItemName.visibility = View.VISIBLE
                binding.chatRoomDetailItemLine.visibility = View.VISIBLE
                binding.chatRoomDetailItemMessage.visibility = View.VISIBLE
                binding.chatRoomDetailItemCreateTime.visibility = View.VISIBLE
            }
            binding.product = message
            binding.root.setOnClickListener { onClickListener.onClick(message) }
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem:Message, newItem: Message): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem:Message, newItem: Message): Boolean {
            return oldItem.createTime == newItem.createTime
        }

        private const val ITEM_VIEW_TYPE_ARTICLE = 0x00
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_ARTICLE -> ChatRoomDetailViewHolder(ItemChatroomMessageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is ChatRoomDetailViewHolder -> {
                holder.bind((getItem(position) as Message), onClickListener)
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return ITEM_VIEW_TYPE_ARTICLE
    }
}