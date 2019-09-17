package com.starwars.android.data.api

import com.starwars.android.data.api.models.GameUnitsResponse
import com.starwars.android.data.api.models.SingleGameUnitResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GameService {

    @GET("unit")
    suspend fun getAllGameUnits(): Response<GameUnitsResponse>

    @GET("unit/{unitId}")
    suspend fun getGameUnit(@Path("unitId") _id: String): Response<SingleGameUnitResponse>

}