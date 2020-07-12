package com.ray.monsterhunter.data

data class ChatRoom (
    val documentId : String = "",
    var image : String = "",
    val roomName : String = "",
    var typeName : String = "",
    val userId : String = "",
    val createTime : Long? = null
) {
}