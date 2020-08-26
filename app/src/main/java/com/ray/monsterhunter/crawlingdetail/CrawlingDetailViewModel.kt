package com.ray.monsterhunter.crawlingdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.R
import com.ray.monsterhunter.data.Crawling
import com.ray.monsterhunter.data.Message
import com.ray.monsterhunter.data.source.MonsterRepository
import com.ray.monsterhunter.network.LoadApiStatus
import com.ray.monsterhunter.util.Logger
import com.ray.monsterhunter.util.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.ray.monsterhunter.data.source.Result

class CrawlingDetailViewModel(
    val repository: MonsterRepository,
    val argument: Crawling
) : ViewModel() {

    private val _crawling = MutableLiveData<Crawling>().apply {
        value = argument
    }
    val crawling: LiveData<Crawling>
        get() = _crawling

    private val _message = MutableLiveData<Message>().apply {
        value = Message()
    }
    val message: LiveData<Message>
        get() = _message

    var liveMessage = MutableLiveData<List<Message>>()

    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")
        _message.value?.userId = UserManager.userData.id.toString()
        _message.value?.image = UserManager.userData.image.toString()
        _message.value?.email = UserManager.userData.email.toString()

        getLiveLeaveMessageResoult()
    }

    fun getLiveLeaveMessageResoult() {
        liveMessage = crawling.value!!.id?.let { repository.getLiveLeaveMessage(it) }!!
        _status.value = LoadApiStatus.DONE
        _refreshStatus.value = false
    }

    fun leaveMessage(message: Message) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = crawling.value?.id?.let { repository.leaveMessage(message, it) }) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                else -> {
                    _error.value = MonsterApplication.instance.getString(R.string.notGood)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }

}
