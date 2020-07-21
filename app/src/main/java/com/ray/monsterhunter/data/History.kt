package com.ray.monsterhunter.data

data class History(
    var documentId : String? = "",
    var finishtime : Long? = null,
    var user1 : String? = "",
    var user1Type: String = "",
    var user2 : String? = "",
    var user2Type: String = "",
    var user3 : String? = "",
    var user3Type: String = "",
    var user4 : String? = "",
    var user4Type: String = "",
    var image : String = "",
    var monsterName : String = "",
    var missionResult : String = "",
    var creatTime : String = ""
    )

data class FriendScore(
    var id : String = "",
    var armsType: String = "",
    var score : String = "",
    var playnumber : String = ""
)



