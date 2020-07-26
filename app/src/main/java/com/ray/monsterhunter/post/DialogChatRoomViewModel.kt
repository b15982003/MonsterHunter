package com.ray.monsterhunter.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.R
import com.ray.monsterhunter.data.ChatRoom
import com.ray.monsterhunter.data.DateTime
import com.ray.monsterhunter.data.source.MonsterRepository
import com.ray.monsterhunter.network.LoadApiStatus
import com.ray.monsterhunter.data.source.Result
import com.ray.monsterhunter.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DialogChatRoomViewModel(val repository: MonsterRepository) : ViewModel() {

    private val _event = MutableLiveData<ChatRoom>().apply {
        value = ChatRoom(dateTime = DateTime())
    }
    val event : LiveData<ChatRoom>
        get() = _event

    val dateTime = MutableLiveData<DateTime>().apply {
        value = DateTime()
    }

    val _leave = MutableLiveData<Boolean>()
    val leave: LiveData<Boolean>
        get() = _leave

    val postMonster = MutableLiveData<Int>()

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        putdateTime()
    }

    fun putdateTime(){
        _event.value?.dateTime?.date = dateTime.value?.date
        _event.value?.dateTime?.time = dateTime.value?.time
    }


    fun pushChatRoom(chatRoom: ChatRoom) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.pushChatRoom(chatRoom)) {
                is Result.Success -> {
                    Logger.i("ok,${chatRoom}")
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    leave(true)
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

    fun leave(needRefresh: Boolean = false) {
        _leave.value = needRefresh
    }

    fun onLeft() {
        _leave.value = null
    }
    fun nothing(){

    }
}
