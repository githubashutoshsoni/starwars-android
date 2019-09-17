package com.starwars.android.data.api

import javax.inject.Inject

class GameRemoteDataSource @Inject constructor(private val service: GameService) : BaseDataSource() {

    suspend fun getAllGameUnits() = getResult { service.getAllGameUnits() }

    suspend fun getGameUnit(_id: String) = getResult { service.getGameUnit(_id) }

}