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
    var missionResult : String = "",
    var startTime : Boolean = false,
    var endToScore : String = ""

    ) : Parcelable