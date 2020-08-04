package com.ray.monsterhunter.data

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue


@Parcelize
data class ChatRoom(
    var documentId: String = "",
    var image: String = "",
    var roomName: String = "",
    var monsterName: String = "",
    var typeName: String = "",
    var userId: String = "",
    var createTime: Long? = null,
    var teammate: MutableList<String> = mutableListOf(),
    var finishTime : Long? = null,
    var dateTime : DateTime? = null,
    var missionResult : String = "",
    var startTime : String = "null",
    var endToScore : String = "false",
    var armsType1 : String? = "",
    var armsType2 : String? = "",
    var armsType3 : String? = "",
    var armsType4 : String? = "",
    var speaker : String = "null"

) : Parcelable