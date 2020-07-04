package com.ray.monsterhunter.data

data class Users (
    var user : User? = null
)

data class User (
    var image : String = "",
    var id : String = "",
    var friendlist : FriendList? = null
)

data class FriendList (
    var image : String = "",
    var id : String = ""

)