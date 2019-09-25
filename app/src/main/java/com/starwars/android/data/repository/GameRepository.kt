package com.starwars.android.data.repository

import androidx.lifecycle.distinctUntilChanged
import com.starwars.android.data.api.GameRemoteDataSource
import com.starwars.android.data.api.models.BattleHistoryRequest
import com.starwars.android.data.resultLiveData
import com.starwars.android.data.room.dao.GameDAO
import com.starwars.android.data.room.models.GameUnit
import javax.inject.Inject

class GameRepository @Inject constructor(private val dao: GameDAO,
                                         private val remoteSource: GameRemoteDataSource) {

    val allGameUnits = resultLiveData(
            databaseQuery = { dao.getAllGameUnits() },
            networkCall = { remoteSource.getAllGameUnits() },
            saveCallResult = { dao.insertMany(it.units) })

    fun gameUnit(id: String) = resultLiveData(
            databaseQuery = { dao.getGameUnit(id) },
            networkCall = { remoteSource.getGameUnit(id) },
            saveCallResult = { dao.insert(it.unit) })
            .distinctUntilChanged()

    fun getAllBattleHistory() = resultLiveData(
            databaseQuery = { dao.getAllBattleHistory() },
            networkCall = { remoteSource.getAllBattleHistory() },
            saveCallResult = { dao.insertManyBattleHistory(it.battleHistory) })

    fun sendBattleHistory(battleHistory: BattleHistoryRequest) = resultLiveData(
            databaseQuery = { dao.getAllBattleHistory() },
            networkCall = { remoteSource.sendBattle(battleHistory) },
            saveCallResult = { })
            .distinctUntilChanged()

    fun updateGameUnit(gameUnit: GameUnit) = resultLiveData(
            databaseQuery = { dao.getAllGameUnits() },
            networkCall = { remoteSource.updateGameUnit(gameUnit) },
            saveCallResult = { remoteSource.getGameUnit(gameUnit._id) })
            .distinctUntilChanged()

}

