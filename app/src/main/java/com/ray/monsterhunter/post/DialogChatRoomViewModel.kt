package com.ray.monsterhunter.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ray.monsterhunter.MainViewModel
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.R
import com.ray.monsterhunter.data.ChatRoom
import com.ray.monsterhunter.data.DateTime
import com.ray.monsterhunter.data.source.MonsterRepository
import com.ray.monsterhunter.network.LoadApiStatus
import com.ray.monsterhunter.data.source.Result
import com.ray.monsterhunter.util.ImageManger
import com.ray.monsterhunter.util.Logger
import com.ray.monsterhunter.util.TimeUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class DialogChatRoomViewModel(val repository: MonsterRepository) : ViewModel() {


    val finalTime = MutableLiveData<Long>()
    val getTime = MutableLiveData<Long>()

    val years = MutableLiveData<Int>()
    val months = MutableLiveData<Int>()
    val days = MutableLiveData<Int>()
    val hours = MutableLiveData<Int>()
    val mins = MutableLiveData<Int>()

    private val _event = MutableLiveData<ChatRoom>().apply {
        value = ChatRoom(dateTime = DateTime())
    }
    val event: LiveData<ChatRoom>
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

    fun putdateTime() {
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

    fun getDate(year: Int, month: Int, day: Int) {
        val dateToStamp = TimeUtil.DateToStamp("$year-${month.plus(1)}-$day", Locale.TAIWAN)
        event.value?.dateTime?.date = dateToStamp
        years.value = year
        months.value = month + 1
        days.value = day
    }

    fun getTime(hour: Int, min: Int) {
        val timeToStamp = TimeUtil.TimeToStamp("$hour:$min", Locale.TAIWAN)
        event.value?.dateTime?.time = timeToStamp
        hours.value = hour
        mins.value = min
        var nowTime =
            "${years.value}-${months.value}-${days.value} " +
                    "${hours.value}:${mins.value}"
        getTime.value = TimeUtil.DateToAllStamp(nowTime, Locale.TAIWAN)
        Logger.d("1")
    }

    // get UserManger have time
    fun getWorkerMangerTime(addTime: String) {
        finalTime.value = getTime.value?.minus(TimeUtil.DateToAllStamp(addTime, Locale.TAIWAN))
        Logger.d("2")

    }

    fun leave(needRefresh: Boolean = false) {
        _leave.value = needRefresh
    }

    fun onLeft() {
        _leave.value = null
    }

    fun nothing() {

    }

    fun setArmsNameByMember(member: Int, armsName: String) {

        when (member) {
            1 -> {
                _event.value?.armsType1 = armsName
            }
            2 -> {
                _event.value?.armsType2 = armsName
            }
            3 -> {
                _event.value?.armsType3 = armsName
            }
            4 -> {
                _event.value?.armsType4 = armsName
            }
        }
    }

    fun setPostTypeName(id: Long) {
        when (id) {
            0L -> _event.value?.typeName = "出擊"
            1L -> _event.value?.typeName = "任務"
            2L -> _event.value?.typeName = "自由"
            3L -> _event.value?.typeName = "調查"
            4L -> _event.value?.typeName = "活動"
            5L -> _event.value?.typeName = "限時活動"
            6L -> _event.value?.typeName = "採集"
        }
    }

    fun setMonsterType(id: Long) {
        when (id) {
            0L -> {
                _event.value?.monsterName = "隨機打"
                postMonster.value = 0
                _event.value?.image = ImageManger.imageData.monsterRoomPost
            }
            1L -> {
                _event.value?.monsterName = "滅盡龍"
                postMonster.value = 1
                _event.value?.image = ImageManger.imageData.monsterRoomPost
            }
            2L -> {
                _event.value?.monsterName = "煌黑龍"
                postMonster.value = 2
                _event.value?.image = ImageManger.imageData.monsterYellowBlack
            }
            3L -> {
                _event.value?.monsterName = "麒麟"
                postMonster.value = 3
                _event.value?.image = ImageManger.imageData.monsterUnico
            }
            4L -> {
                _event.value?.monsterName = "火龍"
                postMonster.value = 4
                _event.value?.image = ImageManger.imageData.monsterFireDragon
            }
            5L -> {
                _event.value?.monsterName = "冰牙龍"
                postMonster.value = 5
                _event.value?.image = ImageManger.imageData.monsterIceteeth
            }
            6L -> {
                _event.value?.monsterName = "冰呪龍"
                postMonster.value = 6
                _event.value?.image = ImageManger.imageData.monsterIcehit
            }
            7L -> {
                _event.value?.monsterName = "大兇豺龍"
                postMonster.value = 7
                _event.value?.image = ImageManger.imageData.monsterMoneyPap
            }
            8L -> {
                _event.value?.monsterName = "土砂龍"
                postMonster.value = 8
                _event.value?.image = ImageManger.imageData.monsterEarthSand
            }
            9L -> {
                _event.value?.monsterName = "大兇顎龍"
                postMonster.value = 9
                _event.value?.image = ImageManger.imageData.monsterBigTooth
            }
            10L -> {
                _event.value?.monsterName = "角龍"
                postMonster.value = 10
                _event.value?.image = ImageManger.imageData.monsterHorned
            }
            11L -> {
                _event.value?.monsterName = "岩賊龍"
                postMonster.value = 11
                _event.value?.image = ImageManger.imageData.monsterBlueDad
            }
            12L -> {
                _event.value?.monsterName = "飛雷龍"
                postMonster.value = 12
                _event.value?.image = ImageManger.imageData.monsterThunder
            }
            13L -> {
                _event.value?.monsterName = "炎王龍"
                postMonster.value = 13
                _event.value?.image = ImageManger.imageData.monsterFireKing
            }
            14L -> {
                _event.value?.monsterName = "泥魚龍"
                postMonster.value = 14
                _event.value?.image = ImageManger.imageData.monsterSoilFish
            }
            15L -> {
                _event.value?.monsterName = "毒妖鳥"
                postMonster.value = 15
                _event.value?.image = ImageManger.imageData.monsterPoison
            }
            16L -> {
                _event.value?.monsterName = "屍套龍"
                postMonster.value = 16
                _event.value?.image = ImageManger.imageData.monsterZombie
            }

        }
    }
}
