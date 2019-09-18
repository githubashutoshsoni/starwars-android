package com.starwars.android.data.api.models

import com.starwars.android.data.room.models.BattleHistory

data class BattleHistoryResponse(
        val battleHistory: List<BattleHistory>,
        val success: Boolean
)