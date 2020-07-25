package com.ray.monsterhunter.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ray.monsterhunter.data.User
import com.ray.monsterhunter.data.source.MonsterRepository
import com.ray.monsterhunter.friendDetail.DialogFriendDetailViewModel


class UserViewModelFactory constructor(
    private val repository: MonsterRepository,
    private val user: User
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) = with(modelClass) {
        when {
            isAssignableFrom(DialogFriendDetailViewModel::class.java) ->
                DialogFriendDetailViewModel(repository,user)


            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }as T
}