package com.ray.monsterhunter.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MonsterUri(
    val monsterFireDragon : String = "",
    val monsterIcehit : String = "",
    val monsterIceteeth : String = "",
    val monsterRoomPost : String = "",
    val monsterUnico : String = "",
    val monsterYellowBlack : String = ""

):Parcelable