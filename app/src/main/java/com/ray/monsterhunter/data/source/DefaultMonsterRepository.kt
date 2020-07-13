package com.ray.monsterhunter.data.source

import androidx.lifecycle.MutableLiveData
import com.ray.monsterhunter.data.Activity
import com.ray.monsterhunter.data.ChatRoom
import com.ray.monsterhunter.data.Crawling
import com.ray.monsterhunter.data.User

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

    override fun getLiveChatRoom(): MutableLiveData<List<ChatRoom>> {
        return remoteDataSource.getLiveChatRoom()
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