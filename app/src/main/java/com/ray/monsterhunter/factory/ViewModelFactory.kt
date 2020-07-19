package com.ray.monsterhunter.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ray.monsterhunter.MainViewModel
import com.ray.monsterhunter.chatroom.ChatRoomViewModel
import com.ray.monsterhunter.chattoomdetail.ChatRoomDetailScoreViewModel
import com.ray.monsterhunter.chattoomdetail.ChatRoomDetailViewModel
import com.ray.monsterhunter.data.source.MonsterRepository
import com.ray.monsterhunter.friend.FriendViewModel
import com.ray.monsterhunter.home.HomeViewModel
import com.ray.monsterhunter.post.DialogChatRoomViewModel
import com.ray.monsterhunter.post.DialogPostViewModel
import com.ray.monsterhunter.profile.ProfileViewModel


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

            isAssignableFrom(ProfileViewModel::class.java) ->
                ProfileViewModel(repository)

            isAssignableFrom(ChatRoomViewModel::class.java) ->
                ChatRoomViewModel(repository)

            isAssignableFrom(DialogChatRoomViewModel::class.java) ->
                DialogChatRoomViewModel(repository)

            isAssignableFrom(ChatRoomDetailScoreViewModel::class.java) ->
                ChatRoomDetailScoreViewModel(repository)

            isAssignableFrom(FriendViewModel::class.java) ->
                FriendViewModel(repository)



            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }as T
}