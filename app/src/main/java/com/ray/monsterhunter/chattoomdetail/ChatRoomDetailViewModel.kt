package com.ray.monsterhunter.chattoomdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.data.ChatRoom
import com.ray.monsterhunter.data.Message
import com.ray.monsterhunter.data.source.MonsterRepository
import com.ray.monsterhunter.network.LoadApiStatus
import com.ray.monsterhunter.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class ChatRoomDetailViewModel(val repository: MonsterRepository) : ViewModel() {

    var livemessage = MutableLiveData<List<Message>>()

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

    fun getMessage() {

    }

    init {
        if(MonsterApplication.instance.isLiveChatRoom()){
            getLiveMessageResoult()
        }else{
            getMessage()

        }
    }

    fun getLiveMessageResoult(){
        livemessage = repository.getLiveMessage()
        Logger.d("see data live ${livemessage.value}")
        Logger.d("see data live22 ${repository.getLiveMessage()}")
        _status.value = LoadApiStatus.DONE
        _refreshStatus.value = false

    }
}

