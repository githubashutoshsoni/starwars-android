package com.starwars.android.data.api.models

import com.starwars.android.data.room.models.GameUnit

data class GameUnitsResponse(
        val success: Boolean,
        val units: List<GameUnit>
)