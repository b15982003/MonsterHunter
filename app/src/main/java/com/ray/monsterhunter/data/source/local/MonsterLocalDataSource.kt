package com.ray.monsterhunter.data.source.local

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.ray.monsterhunter.data.*
import com.ray.monsterhunter.data.source.MonsterDataSource
import com.ray.monsterhunter.data.source.Result

class MonsterLocalDataSource(context: Context) :MonsterDataSource {

    override suspend fun getCrawlings(): Result<List<Crawling>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getActivitys(): Result<List<Activity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLiveChatRoom(): MutableLiveData<List<ChatRoom>> {
        TODO("Not yet implemented")
    }

    override fun getLiveMessage(): MutableLiveData<List<Message>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getImageMonster(): Result<MonsterUri> {
        TODO("Not yet implemented")
    }


    override suspend fun publish(crawling: Crawling): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun pushUser(user: User): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun pushChatRoom(chatRoom: ChatRoom): Result<Boolean> {
        TODO("Not yet implemented")
    }


}