package com.ray.monsterhunter.data.source


import androidx.lifecycle.MutableLiveData
import com.ray.monsterhunter.data.*

interface MonsterDataSource {

    suspend fun getCrawlings(): Result<List<Crawling>>

    suspend fun getActivitys(): Result<List<Activity>>

    suspend fun getUser(): Result<User>

    suspend fun getImageMonster(): Result<MonsterUri>

    fun getLiveChatRoom() : MutableLiveData<List<ChatRoom>>

    fun getLiveMessage() : MutableLiveData<List<Message>>

    suspend fun publish(crawling: Crawling): Result<Boolean>

    suspend fun pushUser(user: User): Result<Boolean>

    suspend fun pushChatRoom(chatRoom: ChatRoom): Result<Boolean>


}