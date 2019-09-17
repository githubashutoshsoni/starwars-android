package com.starwars.android.data.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "units")
data class GameUnit(
        @PrimaryKey val _id: String,
        val name: String,
        val agility: Int = 0,
        val createdAt: String?,
        val description: String?,
        val intelligence: Int = 0,
        val picture: String?,
        val strength: Int = 0,
        val type: String?,
        val updatedAt: String?
)
