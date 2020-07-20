package com.ray.monsterhunter.data.source


import androidx.lifecycle.MutableLiveData
import com.ray.monsterhunter.data.*

interface MonsterRepository {

    suspend fun getCrawlings(): Result<List<Crawling>>

    suspend fun getActivitys(): Result<List<Activity>>

    fun getLiveChatRoom(): MutableLiveData<List<ChatRoom>>

    fun getLiveMessage(document: String): MutableLiveData<List<Message>>

    fun getLiveUserOneScore(teammate: String): MutableLiveData<User>

    fun getLiveUserTwoScore(teammate: String): MutableLiveData<User>

    fun getLiveUserThreeScore(teammate: String): MutableLiveData<User>

    fun getLiveUserFourScore(teammate: String): MutableLiveData<User>

    suspend fun getUser(): Result<User>

   suspend fun getUserOneArms(document: String,teammate:String): Result<UserArms>

    suspend fun getUserTwoArms(document: String,teammate:String): Result<UserArms>

    suspend fun getUserThreeArms(document: String,teammate:String): Result<UserArms>

    suspend fun getUserFourArms(document: String,teammate:String): Result<UserArms>

    suspend fun getAllUser(): Result<List<User>>

    suspend fun getMyUser(document: String): Result<List<User>>

    suspend fun getImageMonster(): Result<MonsterUri>

    suspend fun publish(crawling: Crawling): Result<Boolean>

    suspend fun sentMessage(message: Message,document: String): Result<Boolean>

    suspend fun getUserArms(userArmsType: UserArms,document: String): Result<Boolean>

    suspend fun cancelUser(userArmsType: UserArms,document: String): Result<Boolean>

    suspend fun update1(teamList:List<String>,document: String): Result<Boolean>

    suspend fun pushUser(user: User): Result<Boolean>

    suspend fun pushChatRoom(chatRoom: ChatRoom): Result<Boolean>



}