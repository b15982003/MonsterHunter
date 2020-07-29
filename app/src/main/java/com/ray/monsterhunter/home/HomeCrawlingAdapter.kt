package com.ray.monsterhunter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ray.monsterhunter.data.Crawling
import com.ray.monsterhunter.databinding.ItemHomeBinding
import com.ray.monsterhunter.util.TimeUtil
import java.util.*


class HomeCrawlingAdapter(private val onClickListener: OnClickListener ) :
    ListAdapter<Crawling, RecyclerView.ViewHolder>(DiffCallback) {

    class OnClickListener(val clickListener: (crawling:Crawling) -> Unit) {
        fun onClick(crawling:Crawling) = clickListener(crawling)
    }

    class CrawlingViewHolder(private var binding: ItemHomeBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(crawling: Crawling, onClickListener: OnClickListener) {
            var AllStampTimeToDate = crawling.createTime?.let { TimeUtil.AllStampToDate(it, Locale.TAIWAN) }

            binding.homeDataCreatTime.text = AllStampTimeToDate
            binding.product = crawling
            binding.root.setOnClickListener { onClickListener.onClick(crawling) }
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Crawling>() {
        override fun areItemsTheSame(oldItem: Crawling, newItem: Crawling): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: Crawling, newItem: Crawling): Boolean {
            return oldItem.id == newItem.id
        }

        private const val ITEM_VIEW_TYPE_ARTICLE = 0x00
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_ARTICLE -> CrawlingViewHolder(ItemHomeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is CrawlingViewHolder -> {
                holder.bind((getItem(position) as Crawling), onClickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return ITEM_VIEW_TYPE_ARTICLE
    }
}
