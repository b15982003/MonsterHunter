package com.ray.monsterhunter.friendDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ray.monsterhunter.data.ChatRoom
import com.ray.monsterhunter.data.User
import com.ray.monsterhunter.data.source.MonsterRepository

class DialogFriendDetailViewModel(
    private val repository: MonsterRepository,
    private val argument: User
) : ViewModel() {

    private val _user = MutableLiveData<User>().apply {
        value = argument
    }
    val user : LiveData<User>
        get() = _user


    val _leave = MutableLiveData<Boolean>()
    val leave: LiveData<Boolean>
        get() = _leave

    val _follow = MutableLiveData<Boolean>(false)
    val follow : LiveData<Boolean>
    get() = _follow

    init{

    }

    fun nothing(){

    }
    fun getFollow(){
        _follow.value = true
    }

    fun cencelFollow(){
        _follow.value = false
    }

    fun leave(needRefresh: Boolean = false) {
        _leave.value = needRefresh
    }

    fun onLeft() {
        _leave.value = null
    }
}
