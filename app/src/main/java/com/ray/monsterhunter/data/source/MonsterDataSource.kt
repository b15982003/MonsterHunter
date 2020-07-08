package com.ray.monsterhunter.data.source


import com.ray.monsterhunter.data.Activity
import com.ray.monsterhunter.data.Crawling

interface MonsterDataSource {

    suspend fun getCrawlings(): Result<List<Crawling>>

    suspend fun getActivitys(): Result<List<Activity>>

    suspend fun publish(crawling: Crawling): Result<Boolean>
}