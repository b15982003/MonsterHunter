package com.ray.monsterhunter.data.source


import androidx.lifecycle.MutableLiveData
import com.ray.monsterhunter.data.*

interface MonsterDataSource {

    suspend fun getCrawlings(): Result<List<Crawling>>

    suspend fun getActivitys(): Result<List<Activity>>

    suspend fun getUser(): Result<User>

    suspend fun getAllUser(): Result<List<User>>

    suspend fun getMyUser(document: String): Result<List<User>>

    suspend fun getImageMonster(): Result<MonsterUri>

    fun getLiveChatRoom() : MutableLiveData<List<ChatRoom>>

    fun getLiveMessage(document: String) : MutableLiveData<List<Message>>

    suspend fun publish(crawling: Crawling): Result<Boolean>

    suspend fun sentMessage(message: Message,document: String): Result<Boolean>

    suspend fun getUserArms(userArmsType : UserArms,document: String): Result<Boolean>

    suspend fun cencelUser(userArmsType : UserArms,document: String): Result<Boolean>

    suspend fun update1(teamList:List<String>,document: String): Result<Boolean>

    suspend fun pushUser(user: User): Result<Boolean>

    suspend fun pushChatRoom(chatRoom: ChatRoom): Result<Boolean>


}