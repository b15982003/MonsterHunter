package com.ray.monsterhunter.data

data class ChatRoom (
    val documentId : String = "",
    val image : String = "",
    val roomName : String = "",
    val typeName : String = "",
    val userId : String = "",
    val createTime : Long? = null
) {
}