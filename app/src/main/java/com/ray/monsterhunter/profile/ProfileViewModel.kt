package com.ray.monsterhunter.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.R
import com.ray.monsterhunter.data.User
import com.ray.monsterhunter.data.source.MonsterRepository
import com.ray.monsterhunter.network.LoadApiStatus
import com.ray.monsterhunter.util.ServiceLocator.repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.ray.monsterhunter.data.source.Result
import com.ray.monsterhunter.util.Logger
import com.ray.monsterhunter.util.UserManager

class ProfileViewModel(var repository: MonsterRepository) : ViewModel() {

    var auth = FirebaseAuth.getInstance()

     fun signOut() {

        auth.signOut()
    }


    private val _user = MutableLiveData<User>()

    val user: LiveData<User>
        get() = _user


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
    getUser()
    }

    fun getUser() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getUser()

            _user.value = when (result) {
                is Result.Success -> {
                    Logger.d("succsesssss3")
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    result.data

                }
                is Result.Fail -> {
                    Logger.d("faillllll2")
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
                    Logger.d("erorrrrrr1")
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    Logger.d("nonononnononononono0")
                    _error.value = MonsterApplication.instance.getString(R.string.notGood)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
//            _refreshStatus.value = false
        }
    }




//    fun getUser() {
//
//        coroutineScope.launch {
//
//            _status.value = LoadApiStatus.LOADING
//            Logger.d("userInget111111${user.value}")
//            val result = UserManager.userData.email?.let { repository?.getUser(it) }
//
//            _user.value = when (result) {
//                is Result.Success -> {
//                    Logger.d("userIngetsuccccc${user.value}")
//                    _error.value = null
//                    _status.value = LoadApiStatus.DONE
//                    result.data
//
//                }
//                is Result.Fail -> {
//                    Logger.d("userIngetfaillllll${user.value}")
//                    _error.value = result.error
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//                is Result.Error -> {
//                    Logger.d("userIngeterrorrrr${user.value}")
//                    _error.value = result.exception.toString()
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//                else -> {
//                    Logger.d("userIngetooooooooooooo${user.value}")
//                    _error.value = MonsterApplication.instance.getString(R.string.notGood)
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//            }
////            _refreshStatus.value = false
//        }
//    }


}
