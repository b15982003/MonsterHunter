package com.ray.monsterhunter.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DateTime(
    var date: Long? = null,
    var time :Long? = null
) : Parcelable