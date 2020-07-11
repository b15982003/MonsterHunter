package com.ray.monsterhunter.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.R
import com.ray.monsterhunter.data.Activity
import com.ray.monsterhunter.data.Crawling
import com.ray.monsterhunter.data.DateTime
import com.ray.monsterhunter.data.source.MonsterRepository
import com.ray.monsterhunter.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.ray.monsterhunter.data.source.Result
import com.ray.monsterhunter.util.Logger
import com.ray.monsterhunter.util.TimeUtil
import java.util.*

class HomeViewModel(val repository: MonsterRepository) : ViewModel() {


    private var _crawlings = MutableLiveData<List<Crawling>>()

    val crawlings: LiveData<List<Crawling>>
        get() = _crawlings


    private var _activityImage = MutableLiveData<List<Activity>>()

    val activityImage: LiveData<List<Activity>>
        get() = _activityImage


    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getCrawlingsResult()
        getActivityResult()
    }


    fun getCrawlingsResult() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getCrawlings()

            _crawlings.value = when (result) {
                is Result.Success -> {
                    Logger.d("3333333333")
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    result.data

                }
                is Result.Fail -> {
                    Logger.d("222222222")
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
                    Logger.d("1111111")
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    Logger.d("000000000")
                    _error.value = MonsterApplication.instance.getString(R.string.notGood)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            _refreshStatus.value = false
        }
    }

    fun getActivityResult() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getActivitys()

            _activityImage.value = when (result) {
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
            _refreshStatus.value = false
        }
    }
}
