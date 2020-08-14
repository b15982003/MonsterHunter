package com.ray.monsterhunter.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var image: String? = "",
    var documentId: String? = "",
    var id: String? = "",
    var friendlist: FriendList? = null,
    var email: String? = "",
    var armsType: ArmsType = ArmsType(),
    var allFight : Long? = null,
    var track : String? = "no"
) : Parcelable



@Parcelize
data class FriendList(
    var image: String? = "",
    var id: String? = ""
) : Parcelable