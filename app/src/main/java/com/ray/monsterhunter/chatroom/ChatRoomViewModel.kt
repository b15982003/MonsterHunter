package com.ray.monsterhunter.chatroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.load.engine.Engine
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.data.ChatRoom
import com.ray.monsterhunter.data.source.MonsterRepository
import com.ray.monsterhunter.network.LoadApiStatus
import com.ray.monsterhunter.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job


class ChatRoomViewModel(val repository: MonsterRepository) : ViewModel() {

    var livechatRoom = MutableLiveData<List<ChatRoom>>()

    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun getChatRoom() {

    }

    init {
        if(MonsterApplication.instance.isLiveChatRoom()){
            getLiveChatRoomResoult()
        }else{
            getChatRoom()

        }
    }

    fun getLiveChatRoomResoult(){
        livechatRoom = repository.getLiveChatRoom()
        Logger.d("see data live ${livechatRoom.value}")
        Logger.d("see data live22 ${repository.getLiveChatRoom()}")
        _status.value = LoadApiStatus.DONE
        _refreshStatus.value = false

    }
}
