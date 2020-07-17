package com.ray.monsterhunter.data

data class History(
    var documentId : String? = "",
    var time : String? = "",
    var task : String? = "",
    var friendScore : FriendScore? = null
    )

data class FriendScore(
    var id : String = "",
    var armsType: String = "",
    var score : String = "",
    var playnumber : String = ""
)

