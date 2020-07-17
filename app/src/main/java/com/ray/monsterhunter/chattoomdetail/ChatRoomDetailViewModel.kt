package com.ray.monsterhunter.chattoomdetail

import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.R
import com.ray.monsterhunter.data.ChatRoom
import com.ray.monsterhunter.data.Message
import com.ray.monsterhunter.data.User
import com.ray.monsterhunter.data.source.MonsterRepository
import com.ray.monsterhunter.network.LoadApiStatus
import com.ray.monsterhunter.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.ray.monsterhunter.data.source.Result
import com.ray.monsterhunter.util.UserManager

class ChatRoomDetailViewModel(
    private val repository: MonsterRepository,
    private val argument: ChatRoom
) : ViewModel() {

    private val _chatroom = MutableLiveData<ChatRoom>().apply {
        value = argument
    }
    val chatRoom: LiveData<ChatRoom>
        get() = _chatroom

    var teammateList = mutableListOf<String>()

    var emptySeat = MutableLiveData<Boolean>(false)

    var isGoon = MutableLiveData<Boolean>(false)


    var liveMessage = MutableLiveData<List<Message>>()


    private val _ready = MutableLiveData<Boolean>(false)
    val ready: LiveData<Boolean>
        get() = _ready

    private val _message = MutableLiveData<Message>().apply {
        value = Message()
    }
    val message: LiveData<Message>
        get() = _message


    private val _user = MutableLiveData<User>().apply {
        value = User()
    }
    val user: LiveData<User>
        get() = _user

    private val _timming = MutableLiveData<Boolean>(false)

    val timming: LiveData<Boolean>
        get() = _timming

    private val _timeMin = MutableLiveData<Long>(0)
    val timeMin: LiveData<Long>
        get() = _timeMin


    private val _timeSec = MutableLiveData<Long>(0)
    val timeSec: LiveData<Long>
        get() = _timeSec

    private val _leave = MutableLiveData<Boolean>()

    val leave: LiveData<Boolean>
        get() = _leave


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
        if (MonsterApplication.instance.isLiveMessage()) {
            getLiveMessageResoult()
            Logger.d("1234567890")

        } else {
            getMessage()

        }

        user.value?.email = UserManager.userData.email

        message.value?.userId = UserManager.userData.id.toString()
        message.value?.image = UserManager.userData.image.toString()
        enterUpdate()

    }

    fun getLiveMessageResoult() {
        liveMessage = repository.getLiveMessage(chatRoom.value!!.documentId)
        Logger.d("liveMessage${liveMessage.value}")
        Logger.d("liveMessage${repository.getLiveMessage(chatRoom.value!!.documentId)}")
        _status.value = LoadApiStatus.DONE
        _refreshStatus.value = false

    }

    fun sentMessage(message: Message) {


        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.sentMessage(message, chatRoom.value!!.documentId)) {
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

    fun outLeave() {
        isGoon.value = true
        update1()
    }

    fun enterUpdate() {
        if (chatRoom.value?.teammate?.size!! < 3) {
            emptySeat.value = true
            update1()
        } else {
            getOut()
        }
    }

    fun update1() {


        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.update1(teammateList, chatRoom.value!!.documentId)) {
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


    fun startTimming() {
        _timming.value = true

        for (min in 1..50) {
            for (sec in 1..60) {
                Handler().postDelayed({
                    _timeSec
                }, 1000)

                _timeMin
            }
        }
    }

    fun endTimming() {
        _timming.value = false
        _timeMin.value = 0
        _timeSec.value = 0
    }

    fun getready() {
        _ready.value = true
    }

    fun endreadt() {
        _ready.value = false
    }

    fun getOut() {
        _leave.value = true
    }

    fun getOutFinish() {
        _leave.value = false
    }

}


