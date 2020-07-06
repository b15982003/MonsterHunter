package com.ray.monsterhunter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ray.monsterhunter.data.source.MonsterRepository
import com.ray.monsterhunter.util.CurrentFragmentType


class MainViewModel(repository: MonsterRepository): ViewModel() {




    private val _refresh = MutableLiveData<Boolean>()

    val refresh: LiveData<Boolean>
        get() = _refresh

    // Record current fragment to support data binding
    val currentFragmentType = MutableLiveData<CurrentFragmentType>()

    fun refresh() {
        if (!MonsterApplication.instance.isLiveDataDesign()) {
            _refresh.value = true
        }
    }
    
}