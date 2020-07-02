package com.ray.monsterhunter

import android.app.Application
import com.ray.monsterhunter.data.source.MonsterRepository
import com.ray.monsterhunter.util.ServiceLocator
import kotlin.properties.Delegates

class MonsterApplication : Application() {
    // Depends on the flavor,
    val repository: MonsterRepository
        get() = ServiceLocator.provideRepository(this)

    companion object {
        var instance: MonsterApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun isLiveDataDesign() = true
}