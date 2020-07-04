package com.ray.monsterhunter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ray.monsterhunter.data.source.MonsterRepository
import com.ray.monsterhunter.util.CurrentFragmentType


class MainViewModel(repository: MonsterRepository): ViewModel() {

    // Record current fragment to support data binding
    val currentFragmentType = MutableLiveData<CurrentFragmentType>()


    
}