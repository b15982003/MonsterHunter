package com.ray.monsterhunter.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Crawling(
    var id : String? = "",
    var user : User? = null,
    var profileid : String? = "",
    var roomname : String? = "",
    var type : String? = "",
    var monsterType : String? = "",
    var createTime : String? = "",
    var text : String? = ""

) : Parcelable