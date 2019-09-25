package com.starwars.android.data.api

import com.starwars.android.data.api.models.BattleHistoryRequest
import com.starwars.android.data.room.models.GameUnit
import javax.inject.Inject

class GameRemoteDataSource @Inject constructor(private val service: GameService) : BaseDataSource() {

    suspend fun getAllGameUnits() = getResult { service.getAllGameUnits() }

    suspend fun getGameUnit(_id: String) = getResult { service.getGameUnit(_id) }

    suspend fun sendBattle(data: BattleHistoryRequest) = getResult { service.sendBattleHistory(data) }

    suspend fun getAllBattleHistory() = getResult { service.getAllBattleHistory() }

    suspend fun updateGameUnit(gameUnit: GameUnit) = getResult { service.updateUnit(gameUnit) }

}