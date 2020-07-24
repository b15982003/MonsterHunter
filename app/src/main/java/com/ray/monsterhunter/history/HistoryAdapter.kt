package com.ray.monsterhunter.history

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ray.monsterhunter.R
import com.ray.monsterhunter.data.Activity
import com.ray.monsterhunter.data.History
import com.ray.monsterhunter.databinding.ItemHistoryBinding
import com.ray.monsterhunter.databinding.ItemHomeActiviteBinding
import com.ray.monsterhunter.util.TimeUtil
import java.util.*

class HistoryAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<History, RecyclerView.ViewHolder>(DiffCallback) {

    class OnClickListener(val clickListener: (history: History) -> Unit) {
        fun onClick(history: History) = clickListener(history)
    }

    class HistoryViewHolder(private var binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(history: History, onClickListener: OnClickListener) {
            var AllStampTimeToDate = history.createTime?.let { TimeUtil.AllStampToDate(it, Locale.TAIWAN) }

            binding.historyListCreateTime.text = AllStampTimeToDate

            binding.historyListMonsterShape.setBackgroundResource(
                if (history.missionResult == "true"){
                R.drawable.item_history_image_success_bg
                }else{
                    R.drawable.item_history_image_fail_bg
                }
            )

            binding.historyListBackground.setBackgroundColor(
                if (history.missionResult == "true") {
                    Color.parseColor("#ABDAFC")

                } else {
                    Color.parseColor("#F4C0C6")
                }
            )

            binding.historyListMissionResult.setText(
                if (history.missionResult == "true") {
                    "成功"

                } else {
                    "失敗"
                }
            )
            binding.product = history
            binding.root.setOnClickListener { onClickListener.onClick(history) }
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<History>() {
        override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem.image == newItem.image
        }

        private const val ITEM_VIEW_TYPE_ARTICLE = 0x00
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_ARTICLE -> HistoryViewHolder(
                ItemHistoryBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is HistoryViewHolder -> {
                holder.bind((getItem(position) as History), onClickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return ITEM_VIEW_TYPE_ARTICLE
    }
}
