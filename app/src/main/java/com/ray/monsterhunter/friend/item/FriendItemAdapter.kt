package com.ray.monsterhunter.friend.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ray.monsterhunter.data.User
import com.ray.monsterhunter.databinding.ItemFriendBinding

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * [Product], including computing diffs between lists.
 * @param onClickListener a lambda that takes the
 */
class FriendItemAdapter(val onClickListener: OnClickListener ) :
        ListAdapter<User, FriendItemAdapter.FriendItemViewHolder>(DiffCallback) {

    class FriendItemViewHolder(private var binding: ItemFriendBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.user = user
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Product]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): FriendItemViewHolder {
        return FriendItemViewHolder(ItemFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: FriendItemViewHolder, position: Int) {
        val product = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(product)
        }
        holder.bind(product)
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Product]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Product]
     */
    class OnClickListener(val clickListener: (user:User) -> Unit) {
        fun onClick(user:User) = clickListener(user)
    }
}
