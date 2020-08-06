package com.ray.monsterhunter.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.R
import com.ray.monsterhunter.data.Crawling
import com.ray.monsterhunter.data.User
import com.ray.monsterhunter.data.source.Result
import com.ray.monsterhunter.data.source.MonsterRepository
import com.ray.monsterhunter.network.LoadApiStatus
import com.ray.monsterhunter.util.ImageManger
import com.ray.monsterhunter.util.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DialogPostViewModel(
    private val repository: MonsterRepository
) : ViewModel() {


    val _leave = MutableLiveData<Boolean>()
    val leave: LiveData<Boolean>
        get() = _leave

    private val _crawling = MutableLiveData<Crawling>().apply {
        value = Crawling(user = User())
    }
    val crawling: LiveData<Crawling>
        get() = _crawling

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user


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
        getUserInPost()
    }

    fun getUserInPost() {
        crawling.value?.user?.id = UserManager.userData.id
        crawling.value?.user?.image = UserManager.userData.image
        crawling.value?.user?.email = UserManager.userData.email
    }


    fun publish(crawling: Crawling) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.publish(crawling)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    leave(true)
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

    fun chooseCrawlingType(id: Long) {
        _crawling.value?.let {
            when (id) {
                0L -> it.type = "選擇文章類型"
                1L -> it.type = "最新活動"
                2L -> it.type = "心情分享"
                3L -> it.type = "魔物分析"
                4L -> it.type = "攻略分析"
                5L -> it.type = "神人搜尋"
                5L -> it.type = "趣事分享"
            }
        }
    }

    fun chooseMonsterType(id: Long) {
        _crawling.value?.let {
            when (id) {
                0L -> {
                    it.monsterType = "隨機攻打生物"
                    it.image = ImageManger.imageData.monsterRoomPost
                }
                1L -> {
                    it.monsterType = "滅盡龍"
                    it.image = ImageManger.imageData.monsterRoomPost
                }
                2L -> {
                    it.monsterType = "煌黑龍"
                    it.image =
                        ImageManger.imageData.monsterYellowBlack
                }
                3L -> {
                    it.monsterType = "麒麟"
                    it.image = ImageManger.imageData.monsterUnico
                }
                4L -> {
                    it.monsterType = "火龍"
                    it.image =
                        ImageManger.imageData.monsterFireDragon
                }
                5L -> {
                    it.monsterType = "冰牙龍"
                    it.image = ImageManger.imageData.monsterIceteeth
                }
                6L -> {
                    it.monsterType = "冰呪龍"
                    it.image = ImageManger.imageData.monsterIcehit
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

}
