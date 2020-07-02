package com.ray.monsterhunter.ext

import android.app.Activity
import com.ray.monsterhunter.MonsterApplication
import com.ray.monsterhunter.factory.ViewModelFactory

fun Activity.getVmFactory(): ViewModelFactory {
    val repository = (applicationContext as MonsterApplication).repository
    return ViewModelFactory(repository)
}