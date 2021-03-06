package com.ray.monsterhunter.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ray.monsterhunter.chatroomScore.ChatRoomDetailScoreViewModel
import com.ray.monsterhunter.chatroomdetail.ChatRoomDetailViewModel
import com.ray.monsterhunter.data.ChatRoom
import com.ray.monsterhunter.data.source.MonsterRepository


class RoomNameViewModelFactory constructor(
    private val repository: MonsterRepository,
    private val chatRoom : ChatRoom
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) = with(modelClass) {
        when {
            isAssignableFrom(ChatRoomDetailViewModel::class.java) ->
               ChatRoomDetailViewModel(repository,chatRoom)

            isAssignableFrom(ChatRoomDetailScoreViewModel::class.java) ->
                ChatRoomDetailScoreViewModel(
                    repository,
                    chatRoom
                )


            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }as T
}