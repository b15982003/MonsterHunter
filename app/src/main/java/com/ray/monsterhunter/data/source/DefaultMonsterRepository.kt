package com.ray.monsterhunter.data.source

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

    override fun getLiveMessage(document: String): MutableLiveData<List<Message>> {
        return remoteDataSource.getLiveMessage(document)
    }


    override suspend fun publish(crawling: Crawling): Result<Boolean> {
        return remoteDataSource.publish(crawling)
    }

    override suspend fun sentMessage(message: Message,document: String): Result<Boolean> {
       return remoteDataSource.sentMessage(message,document)
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


    override suspend fun pushUser(user: User): Result<Boolean> {
        return remoteDataSource.pushUser(user)
    }

    override suspend fun pushChatRoom(chatRoom: ChatRoom): Result<Boolean> {
        return remoteDataSource.pushChatRoom(chatRoom)
    }



}