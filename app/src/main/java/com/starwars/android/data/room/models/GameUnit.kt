package com.starwars.android.data.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "units")
data class GameUnit(
        @PrimaryKey val _id: String,
        val name: String,
        val agility: Int = 0,
        val description: String?,
        val intelligence: Int = 0,
        val strength: Int = 0,
        val type: String?,
        val picture: String?,
        val createdAt: String?,
        val updatedAt: String?
)
