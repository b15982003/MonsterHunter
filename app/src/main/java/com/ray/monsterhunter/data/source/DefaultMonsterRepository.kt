package com.ray.monsterhunter.data.source

class DefaultMonsterRepository(private val remoteDataSource: MonsterDataSource,
                               private val localDataSource: MonsterDataSource

):MonsterRepository {
}