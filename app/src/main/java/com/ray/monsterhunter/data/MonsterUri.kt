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
    val monsterYellowBlack : String = "",
    val monsterBlueDad : String = "",
    val monsterMoneyPap : String = "",
    val monsterBigTooth : String = "",
    val monsterEarthSand : String = "",
    val monsterFireKing : String = "",
    val monsterPoison : String = "",
    val monsterSoilFish : String = "",
    val monsterThunder : String = "",
    val monsterZombie : String = "",
    val monsterHorned : String = ""

):Parcelable