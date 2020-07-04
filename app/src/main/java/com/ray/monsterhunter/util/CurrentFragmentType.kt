package com.ray.monsterhunter.util


import com.ray.monsterhunter.R
import com.ray.monsterhunter.util.Util.getString

enum class CurrentFragmentType(val value: String) {
    CHATROOM(getString(R.string.chatroom)),
    FRIEND(getString(R.string.friend)),
    HOME(getString(R.string.home)),
    HISTORY(getString(R.string.history)),
    PROFILE(getString(R.string.profile)),
}