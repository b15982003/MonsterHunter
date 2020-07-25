package com.ray.monsterhunter.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ray.monsterhunter.data.History
import com.ray.monsterhunter.data.source.MonsterRepository
import com.ray.monsterhunter.network.LoadApiStatus
import com.ray.monsterhunter.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class HistoryViewModel(
    val repository: MonsterRepository
) : ViewModel() {

//    private var _history = MutableLiveData<List<History>>()
//    val history: LiveData<List<History>>
//        get() = _history

    var liveHistory = MutableLiveData<List<History>>()

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

        getLiveHistoryResoult()
    }


    fun getLiveHistoryResoult(){
        liveHistory = repository.getLiveHistory()
        Logger.d("see data livehistory ${liveHistory.value}")
        Logger.d("see data livehistory2 ${repository.getLiveHistory()}")
        _status.value = LoadApiStatus.DONE
        _refreshStatus.value = false

    }
}
