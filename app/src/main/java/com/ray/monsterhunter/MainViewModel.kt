package com.ray.monsterhunter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.ray.monsterhunter.data.MonsterUri
import com.ray.monsterhunter.data.User
import com.ray.monsterhunter.data.source.MonsterRepository
import com.ray.monsterhunter.network.LoadApiStatus
import com.ray.monsterhunter.util.CurrentFragmentType
import com.ray.monsterhunter.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import com.ray.monsterhunter.data.source.Result
import com.ray.monsterhunter.util.UserManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class MainViewModel(val repository: MonsterRepository) : ViewModel() {

    // workermanger
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED).build()
    val userInfo = MutableLiveData<User>()

    val _leave = MutableLiveData<Boolean>()
    val leave: LiveData<Boolean>
        get() = _leave

    private var _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private var _image = MutableLiveData<MonsterUri>()
    val image: LiveData<MonsterUri>
        get() = _image


    private val _refresh = MutableLiveData<Boolean>()

    val refresh: LiveData<Boolean>
        get() = _refresh

    // Record current fragment to support data binding
    val currentFragmentType = MutableLiveData<CurrentFragmentType>()

    fun refresh() {
        if (!MonsterApplication.instance.isLiveChatRoom()) {
            _refresh.value = true
        }
    }

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
        pushUser(UserManager.userData)
    }

    fun pushUser(user: User) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.pushUser(user)) {
                is Result.Success -> {
                    Logger.i("okkkkkk,${user}")
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
//                    leave(true)
                }
                is Result.Fail -> {
                    Logger.i("ffffffail")
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                }
                is Result.Error -> {
                    Logger.i("errorrrrrrr")
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                else -> {
                    Logger.i("nooooooo")
                    _error.value = MonsterApplication.instance.getString(R.string.notGood)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }

    fun getUser() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getUser()

            _user.value = when (result) {
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


    fun getImageMonster() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getImageMonster()

            _image.value = when (result) {
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

    fun cancelWorkerManger() {
        WorkManager.getInstance(MonsterApplication.instance).cancelAllWork()
    }

    // start workerManger
    fun startWorkerManger(time: Long) {
        val request = OneTimeWorkRequestBuilder<WorkerManager>()
            .setInitialDelay(time, TimeUnit.MILLISECONDS)
            .setConstraints(constraints).build()
        WorkManager.getInstance(MonsterApplication.instance).enqueue(request)

        fun leave(needRefresh: Boolean = false) {
            _leave.value = needRefresh
        }

        fun onLeft() {
            _leave.value = null
        }

    }
}