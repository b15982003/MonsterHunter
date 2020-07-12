package com.ray.monsterhunter.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ray.monsterhunter.data.ChatRoom
import com.ray.monsterhunter.data.source.MonsterRepository

class DialogChatRoomViewModel(val repository: MonsterRepository) : ViewModel() {

    private val _even = MutableLiveData<ChatRoom>()

    val even: LiveData<ChatRoom>
        get() = _even

    val postMonster = MutableLiveData<Int>()


}
