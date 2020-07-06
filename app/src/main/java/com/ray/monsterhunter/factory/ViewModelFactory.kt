package com.ray.monsterhunter.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ray.monsterhunter.MainViewModel
import com.ray.monsterhunter.data.source.MonsterRepository
import com.ray.monsterhunter.home.HomeViewModel
import com.ray.monsterhunter.post.DialogPost
import com.ray.monsterhunter.post.DialogPostViewModel


class ViewModelFactory constructor(
    private val repository: MonsterRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) = with(modelClass) {
        when {
            isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel(repository)

            isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel(repository)

            isAssignableFrom(DialogPostViewModel::class.java) ->
                DialogPostViewModel(repository)


            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }as T
}