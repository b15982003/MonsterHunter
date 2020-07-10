package com.ray.monsterhunter.data.source.local

import android.content.Context
import com.ray.monsterhunter.data.Activity
import com.ray.monsterhunter.data.Crawling
import com.ray.monsterhunter.data.User
import com.ray.monsterhunter.data.source.MonsterDataSource
import com.ray.monsterhunter.data.source.Result

class MonsterLocalDataSource(context: Context) :MonsterDataSource {

    override suspend fun getCrawlings(): Result<List<Crawling>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getActivitys(): Result<List<Activity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override suspend fun publish(crawling: Crawling): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun pushUser(user: User): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(): Result<User> {
        TODO("Not yet implemented")
    }


}