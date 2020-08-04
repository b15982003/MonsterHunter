package com.ray.monsterhunter.data.source


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ray.monsterhunter.data.*

interface MonsterDataSource {

    suspend fun getCrawlings(): Result<List<Crawling>>

    suspend fun getActivitys(): Result<List<Activity>>

    suspend fun getUser(): Result<User>

    suspend fun getUserOneArms(document: String,teammate:String): Result<UserArms>

    suspend fun getUserTwoArms(document: String,teammate:String): Result<UserArms>

    suspend fun getUserThreeArms(document: String,teammate:String): Result<UserArms>

    suspend fun getUserFourArms(document: String,teammate:String): Result<UserArms>

    suspend fun getAllUser(searchText:String): Result<List<User>>

    suspend fun getMyUser(document: String): Result<List<User>>

    suspend fun getImageMonster(): Result<MonsterUri>

    fun getLiveChatRoom() : MutableLiveData<List<ChatRoom>>

    fun getLiveHistory(): MutableLiveData<List<History>>

    fun getLiveMessage(document: String) : MutableLiveData<List<Message>>

    fun getLiveLeaveMessage(document:String): MutableLiveData<List<Message>>


    fun getLiveUserOneScore(teammate: String): MutableLiveData<User>

    fun getLiveUserTwoScore(teammate: String): MutableLiveData<User>

    fun getLiveUserThreeScore(teammate: String): MutableLiveData<User>

    fun getLiveUserFourScore(teammate: String): MutableLiveData<User>

    fun getLiveChatRoomScore(document: String) : MutableLiveData<ChatRoom>

    suspend fun publish(crawling: Crawling): Result<Boolean>

    suspend fun sentMessage(message: Message,document: String): Result<Boolean>

    suspend fun leaveMessage(message : Message,document :String): Result<Boolean>

    suspend fun deleteRoom(document:String): Result<Boolean>

    suspend fun postFriend(user : User): Result<Boolean>

    suspend fun cancelFriend(user : User): Result<Boolean>

    suspend fun getUserArms(userArmsType : UserArms,document: String): Result<Boolean>

    suspend fun cencelUser(userArmsType : UserArms,document: String): Result<Boolean>

    suspend fun update1(teamList:List<String>,document: String): Result<Boolean>

    suspend fun updateChatRoomInfo(chatRoom: LiveData<ChatRoom>, document: String): Result<Boolean>

    suspend fun updateUserOne(userId : String,userOneScore : ArmsType,allFight : Long): Result<Boolean>

    suspend fun updateUserTwo(userId : String,userTwoScore : ArmsType,allFight : Long): Result<Boolean>

    suspend fun updateUserThree(userId : String,userThreeScore : ArmsType,allFight : Long): Result<Boolean>

    suspend fun updateUserFour(userId : String,userFourScore : ArmsType,allFight : Long): Result<Boolean>

    suspend fun pushUser(user: User): Result<Boolean>

    suspend fun pushChatRoom(chatRoom: ChatRoom): Result<Boolean>

    suspend fun pushHistory1(history: History,email:String):Result<Boolean>

    suspend fun pushHistory2(history: History,email:String):Result<Boolean>

    suspend fun pushHistory3(history: History,email:String): Result<Boolean>

    suspend fun pushHistory4(history: History,email:String): Result<Boolean>



}