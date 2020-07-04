package com.ray.monsterhunter.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import androidx.annotation.RequiresApi
import com.ray.monsterhunter.MonsterApplication

object Util {

    fun isInternetConnected(): Boolean {
        val cm = MonsterApplication.instance
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    fun getString(resourceId: Int): String {
        return MonsterApplication.instance.getString(resourceId)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getColor(resourceId: Int): Int {
        return MonsterApplication.instance.getColor(resourceId)
    }
}