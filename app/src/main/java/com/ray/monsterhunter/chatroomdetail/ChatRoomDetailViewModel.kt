package com.ray.monsterhunter.chatroomdetail

import android.os.Handler
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.R
import com.ray.monsterhunter.data.*
import com.ray.monsterhunter.data.source.MonsterRepository
import com.ray.monsterhunter.network.LoadApiStatus
import com.ray.monsterhunter.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.ray.monsterhunter.data.source.Result
import com.ray.monsterhunter.util.UserManager
import com.ray.monsterhunter.util.nulldata
import com.ray.monsterhunter.util.truedata
import java.util.*

class ChatRoomDetailViewModel(
    private val repository: MonsterRepository,
    private val argument: ChatRoom
) : ViewModel() {

    lateinit var runnable: Runnable
    private var handler = Handler()
    var timeCheck: Long = 0
    private var tts: TextToSpeech? = null

    private val _chatRoom = MutableLiveData<ChatRoom>().apply {
        value = argument
    }
    val chatRoom: LiveData<ChatRoom>
        get() = _chatRoom


    var liveChatRoom = MutableLiveData<ChatRoom>()

    var teammateList = mutableListOf<String>()

    var emptySeat = MutableLiveData<Boolean>(false)

    var isGoon = MutableLiveData<Boolean>(false)

    var liveMessage = MutableLiveData<List<Message>>()

    var userArms = MutableLiveData<Int>()

    private val _speakerReady = MutableLiveData<Boolean>(false)
    val speakerReady: LiveData<Boolean>
        get() = _speakerReady

    private val _speakerDoing = MutableLiveData<Boolean>(false)
    val speakerDoing: LiveData<Boolean>
        get() = _speakerDoing

    private val _userArmsType = MutableLiveData<UserArms>().apply {
        value = UserArms()
    }
    val userArmsType: LiveData<UserArms>
        get() = _userArmsType


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

    private val _timing = MutableLiveData<String>("null")

    val timing: LiveData<String>
        get() = _timing


    private var _timeSec = MutableLiveData<Long>(0)
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


    init {
        getLiveMessageResoult()

        user.value?.email = UserManager.userData.email

        message.value?.userId = UserManager.userData.id.toString()
        message.value?.image = UserManager.userData.image.toString()
        message.value?.email = UserManager.userData.email.toString()

        userArmsType.value?.userId = UserManager.userData.id.toString()
        userArmsType.value?.image = UserManager.userData.image.toString()
        userArmsType.value?.email = UserManager.userData.email.toString()

        enterUpdate()
        getLiveChatRoom()

    }


    fun getLiveChatRoom() {
        liveChatRoom = repository.getLiveChatRoomScore(chatRoom.value!!.documentId)

        Logger.d("liveOne ${repository.getLiveChatRoom()}")
        _status.value = LoadApiStatus.DONE
        _refreshStatus.value = false

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

    fun getUserArms(userArmsType: UserArms) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.getUserArms(userArmsType, chatRoom.value!!.documentId)) {
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

    fun cancelUser(userArmsType: UserArms) {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.cancelUser(userArmsType, chatRoom.value!!.documentId)) {
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
        if (chatRoom.value?.teammate?.size!! < 4) {
            emptySeat.value = true
            update1()
        } else {
            getOut()
        }
    }

    fun getOut() {
        _leave.value = true
    }

    fun getOutFinish() {
        _leave.value = false
    }

    fun getReady() {
        _ready.value = true
    }

    fun endReady() {
        _ready.value = false
    }

    fun checkTeammateNumber() {
        if (liveChatRoom.value?.teammate?.size!! < 4) {
            Toast.makeText(MonsterApplication.instance, "人數不足", Toast.LENGTH_SHORT).show()
        } else {
            startTimming()
            timeNumberGo()
        }
    }

    fun timeNumberGo() {
        runnable = Runnable {
            _timeSec.value = _timeSec.value?.plus(1)
            timeCheck = timeSec.value!!
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }

    fun startTimming() {
        _timing.value = "true"
        _chatRoom.value?.startTime = "true"

    }

    fun endTimming() {
        _timing.value = "false"
        _chatRoom.value?.startTime = "false"
        _chatRoom.value?.finishTime = timeCheck
        _timeSec.value = 0
        handler.removeCallbacks(runnable)
    }

    fun returnTime(missionResult: String) {
        _timing.value = null
        _chatRoom.value?.startTime == nulldata ?: timing.value
        _chatRoom.value?.endToScore = truedata
        _chatRoom.value?.missionResult = missionResult
        endReady()
        isGoon.value = false
        endSpeakerReady()
        Handler().postDelayed({
            updateChatRoomInfo()
        }, 500)
    }

    fun startGameStatus() {
        if (chatRoom.value?.userId == UserManager.userData.id) {
            if (ready.value == false) {
                getReady()
            } else {
                if (timing.value != nulldata) {

                    endReady()
                } else {
                    Toast.makeText(
                        MonsterApplication.instance,
                        MonsterApplication.instance.getString(R.string.chatRoom_detail_gaming),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            Toast.makeText(MonsterApplication.instance, "請找房主趕快開始", Toast.LENGTH_SHORT).show()
        }
    }

    fun getSpeakerStatus() {
        if (speakerReady.value == false) {
            getSpeakerReady()
        } else {
            endSpeakerReady()
        }
    }

    fun getSpeakerReady() {
        _speakerReady.value = true
    }

    fun endSpeakerReady() {
        _speakerReady.value = false
    }

    fun speakerDoing() {
        _speakerDoing.value = true
    }

    fun speakerFree() {
        _speakerDoing.value = false
    }

    fun speakerGo(talk: String) {
        _chatRoom.value?.speaker = talk
        speakerDoing()
        updateChatRoomInfo()
        Handler().postDelayed({
            speakerEnd()
            updateChatRoomInfo()
        }, 7000)
    }

    fun speakerEnd() {
        _chatRoom.value?.speaker = "null"
        speakerFree()
    }

    fun isRoomOwner(email: String ): Boolean {
        message.let {
            return email == UserManager.userData.email
        }
    }

    fun say(userSay: String) {
        //設定發音語言
        tts?.setLanguage(Locale.TAIWAN);
        //語調
        tts?.setPitch(1F);//預設1
        //速度
        tts?.setSpeechRate(1F);//預設1
        //然後 播放
        //TextToSpeech.QUEUE_FLUSH:播放下一句時，前一句直接中斷
        //TextToSpeech.QUEUE_ADD:播放下一句時，等待前一句播完
        tts?.speak(userSay, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun createLanguageTTS() {
        if (tts == null) {
            tts = TextToSpeech(MonsterApplication.instance, TextToSpeech.OnInitListener { arg0 ->
                // TTS 初始化成功
                if (arg0 == TextToSpeech.SUCCESS) {
                    // 目前指定的【語系+國家】TTS, 已下載離線語音檔, 可以離線發音
                    if (tts!!.isLanguageAvailable(Locale.TRADITIONAL_CHINESE) == TextToSpeech.LANG_COUNTRY_AVAILABLE) {
                        tts!!.language = Locale.TRADITIONAL_CHINESE
                    }
                }
            })
        }
    }

    fun canceltts() {
        // 釋放 TTS
        tts?.shutdown()
    }

}


