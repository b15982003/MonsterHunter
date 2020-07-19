package com.ray.monsterhunter.ext

import androidx.fragment.app.Fragment
import androidx.room.Room
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.data.ChatRoom
import com.ray.monsterhunter.factory.RoomNameViewModelFactory
import com.ray.monsterhunter.factory.ViewModelFactory




fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as MonsterApplication).repository

    return ViewModelFactory(repository)
}

fun Fragment.getVmFactory(chatRoom: ChatRoom): RoomNameViewModelFactory {
    val repository = (requireContext().applicationContext as MonsterApplication).repository

    return RoomNameViewModelFactory(repository,chatRoom)
}

