package com.starwars.android.data.repository

import androidx.lifecycle.distinctUntilChanged
import com.starwars.android.data.api.GameRemoteDataSource
import com.starwars.android.data.resultLiveData
import com.starwars.android.data.room.dao.GameDAO
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

}

