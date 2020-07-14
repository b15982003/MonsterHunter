package com.ray.monsterhunter.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ChatRoom (
    var documentId : String = "",
    var image : String = "",
    var roomName : String = "",
    var monsterName : String = "",
    var typeName : String = "",
    var userId : String = "",
    var createTime : Long? = null
):Parcelable