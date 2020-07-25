package com.ray.monsterhunter.chattoomdetail

import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.R
import com.ray.monsterhunter.data.ChatRoom
import com.ray.monsterhunter.data.History
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

    var liveChatRoom = MutableLiveData<ChatRoom>()

    var isDone = MutableLiveData<Boolean>(false)

    private val _chatroom = MutableLiveData<ChatRoom>().apply {
        value = argument
    }
    val chatRoom: LiveData<ChatRoom>
        get() = _chatroom

    val history = MutableLiveData<History>().apply {
        value = History()
    }


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
        getLiveChatRoom()
        Handler().postDelayed({
            chatRoom.value?.endToScore = "false"
            updateChatRoomInfo()
        }, 3000)

        chatRoom.value?.teammate?.get(0)?.let { getUserOneArms() }

        if (chatRoom.value?.teammate?.get(1) != null) {
            getUserTwoArms()
            getLiveUserTwoScore()
        } else {
            Toast.makeText(MonsterApplication.instance, "只有你一個人喔", Toast.LENGTH_SHORT).show()
        }

        if (chatRoom.value?.teammate?.get(2) != null) {
            getUserThreeArms()
            getLiveUserThreeScore()
        } else {
            Toast.makeText(MonsterApplication.instance, "只有兩個人喔", Toast.LENGTH_SHORT).show()
        }

        if (chatRoom.value?.teammate?.get(3) != null) {
            getUserFourArms()
            getLiveUserFourScore()
        } else {
            Toast.makeText(MonsterApplication.instance, "只有三個人喔", Toast.LENGTH_SHORT).show()
        }

        history.value?.documentId = chatRoom.value?.documentId
        history.value?.image = chatRoom.value?.image.toString()
        history.value?.monsterName = chatRoom.value?.monsterName.toString()
        history.value?.finishtime = chatRoom.value?.finishTime
        history.value?.missionResult = chatRoom.value?.missionResult.toString()

        Logger.d("history1111111111111 ${history.value?.user1}")
        Logger.d("history1111111111111 ${user1.value?.userId}")

    }

    fun pushHistory() {

        Logger.i("ok,${history.value}")
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = history.value?.let { repository.pushHistory(it) }) {
                is Result.Success -> {

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

    fun getLiveChatRoom() {
        liveChatRoom = repository.getLiveChatRoomScore(chatRoom.value!!.documentId)
        Logger.d("liveOne ${liveUserOne.value}")
        Logger.d("liveOne ${repository.getLiveChatRoom()}")
        _status.value = LoadApiStatus.DONE
        _refreshStatus.value = false

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
        liveUserThree = repository.getLiveUserOneScore(chatRoom.value!!.teammate[2])
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

    fun updateUserOne() {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = liveUserOne.value?.armsType?.let {
                liveUserOne.value?.email?.let { it1 ->
                    liveUserOne.value!!.allFight?.let { it2 ->

                        repository.updateUserOne(it1, it, it2)
                    }
                }
            }) {
                is Result.Success -> {
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

    fun updateUserTwo() {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = liveUserTwo.value?.armsType?.let {
                liveUserTwo.value?.email?.let { it1 ->
                    liveUserTwo.value!!.allFight?.let { it2 ->

                        repository.updateUserTwo(it1, it, it2)
                    }
                }
            }) {
                is Result.Success -> {
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


    fun updateUserThree() {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = liveUserThree.value?.armsType?.let {
                liveUserThree.value?.email?.let { it1 ->
                    liveUserThree.value!!.allFight?.let { it2 ->

                        repository.updateUserThree(it1, it, it2)
                    }
                }
            }) {
                is Result.Success -> {
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

    fun updateUserFour() {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = liveUserFour.value?.armsType?.let {
                liveUserFour.value?.email?.let { it1 ->
                    liveUserFour.value!!.allFight?.let { it2 ->

                        repository.updateUserFour(it1, it, it2)
                    }
                }
            }) {
                is Result.Success -> {
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

    fun updateChatRoomInfo() {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result =
                repository.updateChatRoomInfo(chatRoom, chatRoom.value!!.documentId)) {
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

