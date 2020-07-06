package com.ray.monsterhunter.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users (
    var user : User? = null
):Parcelable

@Parcelize
data class User (
    var image : String = "",
    var id : String = "",
    var friendlist : FriendList? = null
):Parcelable

@Parcelize
data class FriendList (
    var image : String = "",
    var id : String = ""

):Parcelable