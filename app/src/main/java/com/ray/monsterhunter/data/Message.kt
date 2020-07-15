package com.ray.monsterhunter.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Message(
    var userId: String = "",
    var talk: String = "",
    var createTime: String = ""
):Parcelable