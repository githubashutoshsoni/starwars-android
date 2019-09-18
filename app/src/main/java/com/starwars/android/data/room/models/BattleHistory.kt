package com.starwars.android.data.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class BattleHistory(
        @PrimaryKey val _id: String,
        val createdAt: String,
        val cloneArmyCapacity: Int,
        val droidArmyCapacity: Int,
        val winner: String
)