package com.ray.monsterhunter.ext

import androidx.fragment.app.Fragment
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.data.ChatRoom
import com.ray.monsterhunter.data.User
import com.ray.monsterhunter.factory.FriendItemViewModelFactory
import com.ray.monsterhunter.factory.RoomNameViewModelFactory
import com.ray.monsterhunter.factory.UserViewModelFactory
import com.ray.monsterhunter.factory.ViewModelFactory
import com.ray.monsterhunter.friend.FriendTypeFilter


fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as MonsterApplication).repository

    return ViewModelFactory(repository)
}

fun Fragment.getVmFactory(chatRoom: ChatRoom): RoomNameViewModelFactory {
    val repository = (requireContext().applicationContext as MonsterApplication).repository
    return RoomNameViewModelFactory(repository,chatRoom)
}

fun Fragment.getVmFactory(friendTypeFilter: FriendTypeFilter): FriendItemViewModelFactory {
    val repository = (requireContext().applicationContext as MonsterApplication).repository

    return FriendItemViewModelFactory(repository,friendTypeFilter)
}

fun Fragment.getVmFactory(user : User): UserViewModelFactory {
    val repository = (requireContext().applicationContext as MonsterApplication).repository
    return UserViewModelFactory(repository,user)
}

