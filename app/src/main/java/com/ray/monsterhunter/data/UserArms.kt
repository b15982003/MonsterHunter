package com.ray.monsterhunter.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserArms(
    var userId: String = "",
    var createTime: Long? = null,
    var image: String = "",
    var email: String = "",
    var armsType: String = ""

) : Parcelable