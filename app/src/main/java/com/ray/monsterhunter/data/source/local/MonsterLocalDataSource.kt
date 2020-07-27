package com.ray.monsterhunter.data.source.local

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ray.monsterhunter.data.*
import com.ray.monsterhunter.data.source.MonsterDataSource
import com.ray.monsterhunter.data.source.Result

class MonsterLocalDataSource(context: Context) : MonsterDataSource {

    override suspend fun getCrawlings(): Result<List<Crawling>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getActivitys(): Result<List<Activity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLiveChatRoom(): MutableLiveData<List<ChatRoom>> {
        TODO("Not yet implemented")
    }

    override fun getLiveHistory(): MutableLiveData<List<History>> {
        TODO("Not yet implemented")
    }

    override fun getLiveMessage(document: String): MutableLiveData<List<Message>> {
        TODO("Not yet implemented")
    }

    override fun getLiveUserOneScore(teammate: String): MutableLiveData<User> {
        TODO("Not yet implemented")
    }

    override fun getLiveUserTwoScore(teammate: String): MutableLiveData<User> {
        TODO("Not yet implemented")
    }

    override fun getLiveUserThreeScore(teammate: String): MutableLiveData<User> {
        TODO("Not yet implemented")
    }

    override fun getLiveUserFourScore(teammate: String): MutableLiveData<User> {
        TODO("Not yet implemented")
    }

    override fun getLiveChatRoomScore(document: String): MutableLiveData<ChatRoom> {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserOneArms(teammate: String, document: String): Result<UserArms> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserTwoArms(document: String, teammate: String): Result<UserArms> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserThreeArms(document: String, teammate: String): Result<UserArms> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserFourArms(document: String, teammate: String): Result<UserArms> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllUser(): Result<List<User>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMyUser(document: String): Result<List<User>> {
        TODO("Not yet implemented")
    }

    override suspend fun getImageMonster(): Result<MonsterUri> {
        TODO("Not yet implemented")
    }


    override suspend fun publish(crawling: Crawling): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun sentMessage(message: Message, document: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun leaveMessage(
        message: MutableLiveData<Message>,
        crawling: MutableLiveData<Crawling>
    ) : Result<Boolean>{
        TODO("Not yet implemented")
    }

    override suspend fun postFriend(user: User): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun cancelFriend(user: User): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserArms(userArmsType: UserArms, document: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun cencelUser(userArmsType: UserArms, document: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun update1(teamList: List<String>, document: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updateChatRoomInfo(
        chatRoom: LiveData<ChatRoom>,
        document: String
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserOne(userId: String, userOneScore: ArmsType,allFight : Long): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserTwo(userId: String, userTwoScore: ArmsType,allFight : Long): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserThree(
        userId: String,
        userThreeScore: ArmsType,allFight : Long
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserFour(userId: String, userFourScore: ArmsType,allFight : Long): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun pushUser(user: User): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun pushChatRoom(chatRoom: ChatRoom): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun pushHistory1(history: History,email:String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun pushHistory2(history: History, email: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun pushHistory3(history: History, email: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun pushHistory4(history: History, email: String): Result<Boolean> {
        TODO("Not yet implemented")
    }


}