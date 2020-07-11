package com.ray.monsterhunter.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.R
import com.ray.monsterhunter.data.Crawling
import com.ray.monsterhunter.data.DateTime
import com.ray.monsterhunter.data.FriendList
import com.ray.monsterhunter.data.User
import com.ray.monsterhunter.data.source.Result
import com.ray.monsterhunter.data.source.MonsterRepository
import com.ray.monsterhunter.network.LoadApiStatus
import com.ray.monsterhunter.util.Logger
import com.ray.monsterhunter.util.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DialogPostViewModel(
    private val repository: MonsterRepository
) : ViewModel() {

    val dateTime = MutableLiveData<DateTime>().apply {
        value = DateTime()
    }

    val _leave = MutableLiveData<Boolean>()
    val leave: LiveData<Boolean>
        get() = _leave

    private val _crawling = MutableLiveData<Crawling>().apply {
        value = Crawling(user = User(),dateTime = DateTime())
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
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")
        getUserInPost()

    }

    fun getUserInPost() {
        crawling.value?.user?.id = UserManager.userData.id
        crawling.value?.user?.image = UserManager.userData.image
        crawling.value?.user?.email = UserManager.userData.email
        Logger.d("ppppp${crawling.value?.user?.id}")

    }

    fun putdateTime(){
        crawling.value?.dateTime?.date = dateTime.value?.date
        crawling.value?.dateTime?.time = dateTime.value?.time
    }


    fun publish(crawling: Crawling) {

        putdateTime()

        Logger.d("rrrrrrrrrrrrrrr${dateTime.value}")

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.publish(crawling)) {
                is Result.Success -> {
                    Logger.i("ok,${crawling}")
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

}
