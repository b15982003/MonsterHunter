package com.ray.monsterhunter.data.source


import com.ray.monsterhunter.data.Crawling

interface MonsterDataSource {

    suspend fun getCrawlings(): Result<List<Crawling>>

    suspend fun publish(crawling: Crawling): Result<Boolean>
}