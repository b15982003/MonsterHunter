package com.ray.monsterhunter.friend.item


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.R
import com.ray.monsterhunter.data.User
import com.ray.monsterhunter.data.source.MonsterRepository
import com.ray.monsterhunter.friend.FriendTypeFilter
import com.ray.monsterhunter.network.LoadApiStatus
import com.ray.monsterhunter.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.ray.monsterhunter.data.source.Result
import com.ray.monsterhunter.util.UserManager

class FriendItemViewModel(
    val repository: MonsterRepository,
    val friendTypeFilter: FriendTypeFilter
    // Handle the type for each catalog item
) : ViewModel() {

    val searchText: String? = UserManager.userData.email

    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    var friendEmail = MutableLiveData<String>()

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    private val _userList = MutableLiveData<List<User>>().apply {
        value = listOf(User())
    }
    val userList: MutableLiveData<List<User>>
        get() = _userList

    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")
        if (searchText != null) {
            getUserList(searchText)
        }
    }

    fun getUserList(searchText: String) {
        when (friendTypeFilter) {
            FriendTypeFilter.USERLIST ->{
                if (searchText != null){
                    getAllUser(searchText)
                }
            }
            else -> getMyUser()
        }
    }

    fun getAllUser(searchText : String) {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getAllUser(searchText)

            _userList.value = when (result) {
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
        }
    }

    fun getMyUser() {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getMyUser(UserManager.userData.email!!)

            _userList.value = when (result) {
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
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}