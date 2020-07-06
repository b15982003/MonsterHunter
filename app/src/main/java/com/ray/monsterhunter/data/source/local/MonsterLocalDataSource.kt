package com.ray.monsterhunter.data.source.local

import android.content.Context
import com.ray.monsterhunter.data.Crawling
import com.ray.monsterhunter.data.source.MonsterDataSource
import com.ray.monsterhunter.data.source.Result

class MonsterLocalDataSource(context: Context) :MonsterDataSource {

    override suspend fun getCrawlings(): Result<List<Crawling>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override suspend fun publish(crawling: Crawling): Result<Boolean> {
        TODO("Not yet implemented")
    }


}