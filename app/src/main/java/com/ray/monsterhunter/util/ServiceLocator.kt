package com.ray.monsterhunter.util

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.ray.monsterhunter.data.source.DefaultMonsterRepository
import com.ray.monsterhunter.data.source.MonsterDataSource
import com.ray.monsterhunter.data.source.MonsterRepository
import com.ray.monsterhunter.data.source.local.MonsterLocalDataSource
import com.ray.monsterhunter.data.source.remote.MonsterRemoteDataSource

object ServiceLocator {

    @Volatile
    var repository: MonsterRepository? = null
        @VisibleForTesting set

    fun provideRepository(context: Context): MonsterRepository {
        synchronized(this) {
            return repository
                ?: repository
                ?: createPublisherRepository(context)
        }
    }

    private fun createPublisherRepository(context: Context): MonsterRepository {
        return DefaultMonsterRepository(
            MonsterRemoteDataSource,
            createLocalDataSource(context)
        )
    }

    private fun createLocalDataSource(context: Context): MonsterDataSource {
        return MonsterLocalDataSource(context)
    }
}