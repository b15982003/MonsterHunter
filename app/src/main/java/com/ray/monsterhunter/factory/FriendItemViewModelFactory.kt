package com.ray.monsterhunter.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ray.monsterhunter.data.source.MonsterRepository
import com.ray.monsterhunter.friend.FriendTypeFilter
import com.ray.monsterhunter.friend.item.FriendItemViewModel


class FriendItemViewModelFactory constructor(
    private val repository: MonsterRepository,
    private val friendTypeFilter: FriendTypeFilter
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) = with(modelClass) {
        when {
            isAssignableFrom(FriendItemViewModel::class.java) ->
               FriendItemViewModel(repository,friendTypeFilter)


            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }as T
}