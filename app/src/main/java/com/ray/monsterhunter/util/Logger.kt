package com.ray.monsterhunter.util

import android.util.Log
import com.google.android.gms.common.api.ApiException
import com.ray.monsterhunter.BuildConfig


object Logger {

    private const val TAG = "Wayne-Publisher"

    fun v(content: String) { if (BuildConfig.LOGGER_VISIABLE) Log.v(TAG, content) }
    fun d(content: String) { if (BuildConfig.LOGGER_VISIABLE) Log.d(TAG, content) }
    fun i(content: String) { if (BuildConfig.LOGGER_VISIABLE) Log.i(TAG, content) }
    fun w(content: String, e: ApiException) { if (BuildConfig.LOGGER_VISIABLE) Log.w(TAG, content) }
    fun e(content: String) { if (BuildConfig.LOGGER_VISIABLE) Log.e(TAG, content) }

}