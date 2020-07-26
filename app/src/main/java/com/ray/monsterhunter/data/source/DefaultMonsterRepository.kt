package com.ray.monsterhunter.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ray.monsterhunter.data.*

class DefaultMonsterRepository(
    private val remoteDataSource: MonsterDataSource,
    private val localDataSource: MonsterDataSource

) : MonsterRepository {

    override suspend fun getCrawlings(): Result<List<Crawling>> {
       return remoteDataSource.getCrawlings()
    }

    override suspend fun getActivitys(): Result<List<Activity>> {
        return remoteDataSource.getActivitys()
    }

    override suspend fun getUser(): Result<User> {
        return remoteDataSource.getUser()
    }

    override suspend fun getUserOneArms(document: String,teammate:String): Result<UserArms> {
        return remoteDataSource.getUserOneArms(teammate,document)
    }

    override suspend fun getUserTwoArms(document: String,teammate:String): Result<UserArms> {
        return remoteDataSource.getUserOneArms(teammate,document)
    }

    override suspend fun getUserThreeArms(document: String,teammate:String): Result<UserArms> {
        return remoteDataSource.getUserOneArms(teammate,document)
    }

    override suspend fun getUserFourArms(document: String,teammate:String): Result<UserArms> {
        return remoteDataSource.getUserOneArms(teammate,document)
    }

    override fun getLiveUserOneScore(teammate: String): MutableLiveData<User> {
        return remoteDataSource.getLiveUserOneScore(teammate)
    }

    override fun getLiveUserTwoScore(teammate: String): MutableLiveData<User> {
        return remoteDataSource.getLiveUserTwoScore(teammate)
    }

    override fun getLiveUserThreeScore(teammate: String): MutableLiveData<User> {
        return remoteDataSource.getLiveUserThreeScore(teammate)
    }

    override fun getLiveUserFourScore(teammate: String): MutableLiveData<User> {
        return remoteDataSource.getLiveUserFourScore(teammate)
    }

    override suspend fun getAllUser(): Result<List<User>> {
        return remoteDataSource.getAllUser()
    }

    override suspend fun getMyUser(document: String): Result<List<User>> {
        return remoteDataSource.getMyUser(document)
    }

    override suspend fun getImageMonster(): Result<MonsterUri> {
       return remoteDataSource.getImageMonster()
    }

    override fun getLiveChatRoom(): MutableLiveData<List<ChatRoom>> {
        return remoteDataSource.getLiveChatRoom()
    }

    override fun getLiveHistory(): MutableLiveData<List<History>> {
        return remoteDataSource.getLiveHistory()
    }

    override fun getLiveChatRoomScore(document: String): MutableLiveData<ChatRoom> {
        return remoteDataSource.getLiveChatRoomScore(document)
    }

    override fun getLiveMessage(document: String): MutableLiveData<List<Message>> {
        return remoteDataSource.getLiveMessage(document)
    }


    override suspend fun publish(crawling: Crawling): Result<Boolean> {
        return remoteDataSource.publish(crawling)
    }

    override suspend fun sentMessage(message: Message,document: String): Result<Boolean> {
       return remoteDataSource.sentMessage(message,document)
    }

    override suspend fun postFriend(user: User): Result<Boolean> {
        return remoteDataSource.postFriend(user)
    }

    override suspend fun cancelFriend(user: User): Result<Boolean> {
        return remoteDataSource.cancelFriend(user)
    }

    override suspend fun getUserArms(userArmsType: UserArms, document: String): Result<Boolean> {
        return remoteDataSource.getUserArms(userArmsType, document)
    }

    override suspend fun cancelUser(userArmsType: UserArms, document: String): Result<Boolean> {
       return remoteDataSource.cencelUser(userArmsType,document)
    }

    override suspend fun update1(teamList:List<String>,document: String): Result<Boolean> {
        return remoteDataSource.update1(teamList,document)
    }

    override suspend fun updateChatRoomInfo(
        chatRoom: LiveData<ChatRoom>,
        document: String
    ): Result<Boolean> {
        return remoteDataSource.updateChatRoomInfo(chatRoom,document)
    }

    override suspend fun updateUserOne(userId: String,userOneScore : ArmsType,allFight : Long): Result<Boolean> {
        return remoteDataSource.updateUserOne(userId,userOneScore,allFight)
    }

    override suspend fun updateUserTwo(userId: String,userTwoScore : ArmsType,allFight : Long): Result<Boolean> {
        return remoteDataSource.updateUserTwo(userId,userTwoScore,allFight)
    }

    override suspend fun updateUserThree(userId: String,userThreeScore : ArmsType,allFight : Long): Result<Boolean> {
        return remoteDataSource.updateUserThree(userId,userThreeScore,allFight)
    }

    override suspend fun updateUserFour(userId: String,userFourScore : ArmsType,allFight : Long): Result<Boolean> {
        return remoteDataSource.updateUserFour(userId,userFourScore,allFight)
    }


    override suspend fun pushUser(user: User): Result<Boolean> {
        return remoteDataSource.pushUser(user)
    }

    override suspend fun pushChatRoom(chatRoom: ChatRoom): Result<Boolean> {
        return remoteDataSource.pushChatRoom(chatRoom)
    }

    override suspend fun pushHistory1(history: History,email:String): Result<Boolean> {
        return remoteDataSource.pushHistory1(history,email)
    }

    override suspend fun pushHistory2(history: History,email:String): Result<Boolean> {
        return remoteDataSource.pushHistory2(history,email)
    }


}