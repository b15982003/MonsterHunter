package com.ray.monsterhunter.util

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ray.monsterhunter.R
import com.ray.monsterhunter.chatroom.ChatRoomAdapter
import com.ray.monsterhunter.chattoomdetail.ChatRoomDetailAdapter
import com.ray.monsterhunter.crawlingdetail.CrawlingDetailAdapter
import com.ray.monsterhunter.data.*
import com.ray.monsterhunter.friend.item.FriendItemAdapter
import com.ray.monsterhunter.history.HistoryAdapter
import com.ray.monsterhunter.home.HomeActivityAdapter
import com.ray.monsterhunter.home.HomeCrawlingAdapter
import com.ray.monsterhunter.network.LoadApiStatus


/**
 * Created by Wayne Chen on 2020-01-15.
 */
@BindingAdapter("crawlings")
fun bindRecyclerView(recyclerView: RecyclerView, crawlings : List<Crawling>?) {
    crawlings?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is HomeCrawlingAdapter -> submitList(it)
            }
        }
    }
}

@BindingAdapter("chatRoom")
fun bindChatRoomRecycleView(recyclerView: RecyclerView,chatRoom: List<ChatRoom>?){
    Logger.d("bindChatRoomRecycleView, chatRoom=$chatRoom")
    chatRoom?.let {
        recyclerView.adapter?.apply {
            when (this){
                is ChatRoomAdapter -> submitList(it)
            }
        }
    }
}

@BindingAdapter("history")
fun bindHistoryRecyclerView(recyclerView: RecyclerView, history: List<History>?) {
    history?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is HistoryAdapter -> submitList(it)
            }
        }
    }
}


@BindingAdapter("chatRoomMessage")
fun bindChatRoomMessageRecycleView(recyclerView: RecyclerView,message: List<Message>?){
    message?.let {
        recyclerView.adapter?.apply {
            when (this){
                is ChatRoomDetailAdapter -> submitList(it)
            }
        }
    }
}

@BindingAdapter("crawlingleaveMessage")
fun bindCrawlingMessageRecycleView(recyclerView: RecyclerView,message: List<Message>?){
    message?.let {
        recyclerView.adapter?.apply {
            when (this){
                is CrawlingDetailAdapter -> submitList(it)
            }
        }
    }
}

@BindingAdapter("activity")
fun bindActivityRecyclerView(recyclerView: RecyclerView, activity : List<Activity>?) {
    activity?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is HomeActivityAdapter -> submitList(it)
            }
        }
    }
}

@BindingAdapter("getAllUser")
fun bindGetAllUserRecyclerView(recyclerView: RecyclerView, user : List<User>?) {
    user?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is FriendItemAdapter -> submitList(it)
            }
        }
    }
}

/**
 * According to [LoadApiStatus] to decide the visibility of [ProgressBar]
 */
@BindingAdapter("setupApiStatus")
fun bindApiStatus(view: ProgressBar, status: LoadApiStatus?) {
    when (status) {
        LoadApiStatus.LOADING -> view.visibility = View.VISIBLE
        LoadApiStatus.DONE, LoadApiStatus.ERROR -> view.visibility = View.GONE
    }
}

/**
 * According to [message] to decide the visibility of [ProgressBar]
 */
@BindingAdapter("setupApiErrorMessage")
fun bindApiErrorMessage(view: TextView, message: String?) {
    when (message) {
        null, "" -> {
            view.visibility = View.GONE
        }
        else -> {
            view.text = message
            view.visibility = View.VISIBLE
        }
    }
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_other_fire)
                    .error(R.drawable.ic_monster))
            .into(imgView)
    }
}
