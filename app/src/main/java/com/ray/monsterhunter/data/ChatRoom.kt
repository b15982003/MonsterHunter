package com.ray.monsterhunter.data

data class ChatRoom (
    var documentId : String = "",
    var image : String = "",
    val roomName : String = "",
    val monsterName : String = "",
    var typeName : String = "",
    val userId : String = "",
    var createTime : Long? = null
) {
}