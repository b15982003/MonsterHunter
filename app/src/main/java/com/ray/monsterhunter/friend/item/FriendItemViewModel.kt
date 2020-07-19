package com.ray.monsterhunter.friend.item


import androidx.lifecycle.ViewModel
import com.ray.monsterhunter.data.source.MonsterRepository
import com.ray.monsterhunter.friend.FriendTypeFilter
import com.ray.monsterhunter.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class FriendItemViewModel(
    private val repository: MonsterRepository,
    val friendTypeFilter: FriendTypeFilter // Handle the type for each catalog item
) : ViewModel() {

//    // Handle load api status
//    val status: LiveData<LoadApiStatus> = Transformations.switchMap(sourceFactory.sourceLiveData) {
//        it.statusInitialLoad
//    }
//
//    // Handle load api error
//    val error: LiveData<String> = Transformations.switchMap(sourceFactory.sourceLiveData) {
//        it.errorInitialLoad
//    }

    // Handle navigation to detail
//    private val _navigateToDetail = MutableLiveData<Product>()
//
//    val navigateToDetail: LiveData<Product>
//        get() = _navigateToDetail

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")
    }

    fun getUserList(){


        when(friendTypeFilter){
            FriendTypeFilter.USERLIST ->"55"
                else -> "44"

        }

    }

    fun getAllUser(){
        
    }
//    fun refresh() {
//        if (status.value != LoadApiStatus.LOADING) {
//            sourceFactory.sourceLiveData.value?.invalidate()
//        }
//    }

//    fun navigateToDetail(product: Product) {
//        _navigateToDetail.value = product
//    }
//
//    fun onDetailNavigated() {
//        _navigateToDetail.value = null
//    }
}