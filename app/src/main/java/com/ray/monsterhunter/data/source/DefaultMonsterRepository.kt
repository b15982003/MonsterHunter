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

    override suspend fun getImageMonster(): Result<MonsterUri> {
       return remoteDataSource.getImageMonster()
    }

    override fun getLiveChatRoom(): MutableLiveData<List<ChatRoom>> {
        return remoteDataSource.getLiveChatRoom()
    }

    override fun getLiveMessage(): MutableLiveData<List<Message>> {
        return remoteDataSource.getLiveMessage()
    }


    override suspend fun publish(crawling: Crawling): Result<Boolean> {
        return remoteDataSource.publish(crawling)
    }

    override suspend fun pushUser(user: User): Result<Boolean> {
        return remoteDataSource.pushUser(user)
    }

    override suspend fun pushChatRoom(chatRoom: ChatRoom): Result<Boolean> {
        return remoteDataSource.pushChatRoom(chatRoom)
    }



}