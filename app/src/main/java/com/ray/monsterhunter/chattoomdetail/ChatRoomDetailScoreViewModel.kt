package com.ray.monsterhunter.chattoomdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ray.monsterhunter.data.ChatRoom
import com.ray.monsterhunter.data.source.MonsterRepository

class ChatRoomDetailScoreViewModel (
    private val repository: MonsterRepository,
    private val argument : ChatRoom
): ViewModel() {

    private val _chatroom = MutableLiveData<ChatRoom>().apply {
        value = argument
    }
    val chatRoom : LiveData<ChatRoom>
        get() = _chatroom

    init {

    }

    fun getUserOneScore(){
        
    }
}

