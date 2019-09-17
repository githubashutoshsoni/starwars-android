package com.starwars.android.data.api.models

import com.starwars.android.data.room.models.GameUnit

data class SingleGameUnitResponse(
        val success: Boolean,
        val unit: GameUnit
)