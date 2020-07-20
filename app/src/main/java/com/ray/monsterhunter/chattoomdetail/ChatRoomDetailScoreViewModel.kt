package com.ray.monsterhunter.chattoomdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.R
import com.ray.monsterhunter.data.ChatRoom
import com.ray.monsterhunter.data.User
import com.ray.monsterhunter.data.UserArms
import com.ray.monsterhunter.data.source.MonsterRepository
import com.ray.monsterhunter.network.LoadApiStatus
import com.ray.monsterhunter.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.ray.monsterhunter.data.source.Result

class ChatRoomDetailScoreViewModel(
    private val repository: MonsterRepository,
    private val argument: ChatRoom
) : ViewModel() {

    var liveUserOne = MutableLiveData<User>()

    var liveUserTwo = MutableLiveData<User>()

    var liveUserThree = MutableLiveData<User>()

    var liveUserFour = MutableLiveData<User>()

    private val _chatroom = MutableLiveData<ChatRoom>().apply {
        value = argument
    }
    val chatRoom: LiveData<ChatRoom>
        get() = _chatroom

    private val _user1 = MutableLiveData<UserArms>().apply {
        value = UserArms()
    }
    val user1: LiveData<UserArms>
        get() = _user1

    private val _user2 = MutableLiveData<UserArms>().apply {
        value = UserArms()
    }
    val user2: LiveData<UserArms>
        get() = _user2

    private val _user3 = MutableLiveData<UserArms>().apply {
        value = UserArms()
    }
    val user3: LiveData<UserArms>
        get() = _user3

    private val _user4 = MutableLiveData<UserArms>().apply {
        value = UserArms()
    }
    val user4: LiveData<UserArms>
        get() = _user4


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

        chatRoom.value?.teammate?.get(0)?.let { getUserOneArms() }
        chatRoom.value?.teammate?.get(1)?.let { getUserTwoArms() }
        chatRoom.value?.teammate?.get(2)?.let { getUserThreeArms() }
        chatRoom.value?.teammate?.get(3)?.let { getUserFourArms() }

        getLiveUserOneScore()
        getLiveUserTwoScore()
        getLiveUserThreeScore()
        getLiveUserFourScore()

    }

    fun getUserOneArms() {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result =
                repository.getUserOneArms(chatRoom.value!!.teammate[0], chatRoom.value!!.documentId)

            _user1.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    result.data

                }
                is Result.Fail -> {

                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {

                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {

                    _error.value = MonsterApplication.instance.getString(R.string.notGood)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
//            _refreshStatus.value = false
        }

    }

    fun getUserTwoArms() {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result =
                repository.getUserOneArms(chatRoom.value!!.teammate[1], chatRoom.value!!.documentId)

            _user2.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    result.data

                }
                is Result.Fail -> {

                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {

                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {

                    _error.value = MonsterApplication.instance.getString(R.string.notGood)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
//            _refreshStatus.value = false
        }


    }

    fun getUserThreeArms() {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result =
                repository.getUserOneArms(chatRoom.value!!.teammate[2], chatRoom.value!!.documentId)

            _user3.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    result.data

                }
                is Result.Fail -> {

                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {

                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {

                    _error.value = MonsterApplication.instance.getString(R.string.notGood)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
//            _refreshStatus.value = false
        }


    }

    fun getUserFourArms() {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result =
                repository.getUserOneArms(chatRoom.value!!.teammate[3], chatRoom.value!!.documentId)

            _user4.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    result.data

                }
                is Result.Fail -> {

                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {

                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {

                    _error.value = MonsterApplication.instance.getString(R.string.notGood)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
//            _refreshStatus.value = false
        }


    }

    fun getLiveUserOneScore() {
            liveUserOne = repository.getLiveUserOneScore(chatRoom.value!!.teammate[0])
            Logger.d("liveOne ${liveUserOne.value}")
            Logger.d("liveOne ${repository.getLiveChatRoom()}")
            _status.value = LoadApiStatus.DONE
            _refreshStatus.value = false

        }

    fun getLiveUserTwoScore() {
        liveUserTwo = repository.getLiveUserOneScore(chatRoom.value!!.teammate[1])
        Logger.d("liveOne ${liveUserOne.value}")
        Logger.d("liveOne ${repository.getLiveChatRoom()}")
        _status.value = LoadApiStatus.DONE
        _refreshStatus.value = false

    }

    fun getLiveUserThreeScore() {
        liveUserThree= repository.getLiveUserOneScore(chatRoom.value!!.teammate[2])
        Logger.d("liveOne ${liveUserOne.value}")
        Logger.d("liveOne ${repository.getLiveChatRoom()}")
        _status.value = LoadApiStatus.DONE
        _refreshStatus.value = false

    }

    fun getLiveUserFourScore() {
        liveUserFour = repository.getLiveUserOneScore(chatRoom.value!!.teammate[3])
        Logger.d("liveOne ${liveUserOne.value}")
        Logger.d("liveOne ${repository.getLiveChatRoom()}")
        _status.value = LoadApiStatus.DONE
        _refreshStatus.value = false

    }
}

