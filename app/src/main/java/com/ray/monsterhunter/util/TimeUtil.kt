package com.ray.monsterhunter.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {
    @JvmStatic
    fun StampToDate(time: Long, locale: Locale): String {
        // 進來的time以秒為單位，Date輸入為毫秒為單位，要注意

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", locale)

        return simpleDateFormat.format(Date(time))
    }

    @JvmStatic
    fun DateToStamp(date: String, locale: Locale): Long {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", locale)

        /// 輸出為毫秒為單位
        return simpleDateFormat.parse(date).time
    }

    @JvmStatic
    fun StampToTime(time: Long, locale: Locale): String {
        // 進來的time以秒為單位，Date輸入為毫秒為單位，要注意

        val simpleDateFormat = SimpleDateFormat("HH:mm", locale)

        return simpleDateFormat.format(Date(time))
    }

    @JvmStatic
    fun TimeToStamp(time: String, locale: Locale): Long {
        val simpleDateFormat = SimpleDateFormat("HH:mm", locale)

        /// 輸出為毫秒為單位
        return simpleDateFormat.parse(time).time
    }


}
