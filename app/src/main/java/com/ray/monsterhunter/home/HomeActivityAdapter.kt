package com.ray.monsterhunter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ray.monsterhunter.data.Activity
import com.ray.monsterhunter.databinding.ItemHomeActiviteBinding

class HomeActivityAdapter(private val onClickListener: OnClickListener ) :
    ListAdapter<Activity, RecyclerView.ViewHolder>(DiffCallback) {

    class OnClickListener(val clickListener: (activity : Activity) -> Unit) {
        fun onClick(activity: Activity) = clickListener(activity)
    }

    class ActivityViewHolder(private var binding: ItemHomeActiviteBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(activity: Activity, onClickListener: OnClickListener) {

            binding.product = activity
            binding.root.setOnClickListener { onClickListener.onClick(activity) }
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Activity>() {
        override fun areItemsTheSame(oldItem: Activity, newItem: Activity): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: Activity, newItem: Activity): Boolean {
            return oldItem.image == newItem.image
        }
        private const val ITEM_VIEW_TYPE_ARTICLE = 0x00
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_ARTICLE -> ActivityViewHolder(ItemHomeActiviteBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ActivityViewHolder -> {
                holder.bind((getItem(position) as Activity), onClickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return ITEM_VIEW_TYPE_ARTICLE
    }
}
