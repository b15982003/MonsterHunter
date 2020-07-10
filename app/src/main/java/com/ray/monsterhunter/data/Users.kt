package com.ray.monsterhunter.data

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users(
    var user: User? = null
) : Parcelable

@Parcelize
data class User(
    var image: String? = "",
    var documentId: String? = "",
    var id: String? = "",
    var friendlist: FriendList? = null,
    var email: String? = ""
) : Parcelable

@Parcelize
data class FriendList(
    var image: String? = "",
    var id: String? = ""

) : Parcelable