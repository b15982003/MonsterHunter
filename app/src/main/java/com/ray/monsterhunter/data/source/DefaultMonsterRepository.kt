package com.ray.monsterhunter.data.source

import com.ray.monsterhunter.data.Activity
import com.ray.monsterhunter.data.Crawling

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


    override suspend fun publish(crawling: Crawling): Result<Boolean> {
        return remoteDataSource.publish(crawling)
    }

}