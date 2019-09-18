package com.starwars.android.data.api.models

data class BattleHistoryRequest(
        val cloneArmyCapacity: Int,
        val droidArmyCapacity: Int,
        val winner: String
)