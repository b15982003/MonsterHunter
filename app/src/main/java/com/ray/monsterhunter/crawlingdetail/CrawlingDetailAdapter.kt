package com.ray.monsterhunter.crawlingdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ray.monsterhunter.data.ChatRoom
import com.ray.monsterhunter.data.Message
import com.ray.monsterhunter.databinding.ItemCrawlingDetailLeaveMessageBinding
import com.ray.monsterhunter.util.Logger
import com.ray.monsterhunter.util.TimeUtil
import java.util.*

class CrawlingDetailAdapter(
    private val onClickListener: OnClickListener
) :
    ListAdapter<Message, RecyclerView.ViewHolder>(DiffCallback) {

    class OnClickListener(val clickListener: (message : Message) -> Unit) {
        fun onClick(message: Message) = clickListener(message)
    }

    class CrawlingDetailViewHolder(private var binding: ItemCrawlingDetailLeaveMessageBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message, onClickListener: OnClickListener) {

            var AllStampTimeToDate = message.createTime?.let { TimeUtil.AllStampToDate(it, Locale.TAIWAN) }

            binding.leaveMessageCreateTime.text = AllStampTimeToDate
            binding.product = message
            binding.root.setOnClickListener { onClickListener.onClick(message) }
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }

        private const val ITEM_VIEW_TYPE_ARTICLE = 0x00
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_ARTICLE -> CrawlingDetailViewHolder(ItemCrawlingDetailLeaveMessageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is CrawlingDetailViewHolder -> {
                Logger.i("position=$position, name=${getItem(position) as Message}")
                holder.bind((getItem(position) as Message), onClickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return ITEM_VIEW_TYPE_ARTICLE
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

}
