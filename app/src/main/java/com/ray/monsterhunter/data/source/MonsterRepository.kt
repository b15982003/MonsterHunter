package com.ray.monsterhunter.data.source


import com.ray.monsterhunter.data.Activity
import com.ray.monsterhunter.data.Crawling
import com.ray.monsterhunter.data.User

interface MonsterRepository {

    suspend fun getCrawlings(): Result<List<Crawling>>

    suspend fun getActivitys(): Result<List<Activity>>


    suspend fun publish(crawling: Crawling): Result<Boolean>

    suspend fun pushUser(user: User): Result<Boolean>

    suspend fun getUser(): Result<User>

}