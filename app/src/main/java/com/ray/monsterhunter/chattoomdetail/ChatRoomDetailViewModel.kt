package com.ray.monsterhunter.chattoomdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.R
import com.ray.monsterhunter.data.ChatRoom
import com.ray.monsterhunter.data.Message
import com.ray.monsterhunter.data.source.MonsterRepository
import com.ray.monsterhunter.network.LoadApiStatus
import com.ray.monsterhunter.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.ray.monsterhunter.data.source.Result
import com.ray.monsterhunter.util.UserManager

class ChatRoomDetailViewModel(val repository: MonsterRepository) : ViewModel() {

    var liveMessage = MutableLiveData<List<Message>>()

    private val _message = MutableLiveData<Message>().apply {
        value = Message()
    }
    val message: LiveData<Message>
        get() = _message


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
        if(MonsterApplication.instance.isLiveMessage()){
            getLiveMessageResoult()
            Logger.d("1234567890")

        }else{
            getMessage()

        }
        message.value?.userId = UserManager.userData.id.toString()
    }

    fun getLiveMessageResoult(){
        liveMessage = repository.getLiveMessage()
        Logger.d("liveMessage${liveMessage.value}")
        Logger.d("liveMessage${repository.getLiveMessage()}")
        _status.value = LoadApiStatus.DONE
        _refreshStatus.value = false

    }

    fun sentMessage(message: Message) {


        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.sentMessage(message)) {
                is Result.Success -> {
                    Logger.i("ok,${message}")
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                }
                is Result.Fail -> {
                    Logger.i("fail")
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                }
                is Result.Error -> {
                    Logger.i("error")
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                else -> {
                    Logger.i("no")
                    _error.value = MonsterApplication.instance.getString(R.string.notGood)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }

}

