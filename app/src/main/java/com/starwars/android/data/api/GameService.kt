package com.starwars.android.data.api

import com.starwars.android.data.api.models.*
import com.starwars.android.data.room.models.GameUnit
import retrofit2.Response
import retrofit2.http.*

interface GameService {

    @GET("unit")
    suspend fun getAllGameUnits(): Response<GameUnitsResponse>

    @GET("unit/{unitId}")
    suspend fun getGameUnit(@Path("unitId") _id: String): Response<SingleGameUnitResponse>

    @POST("battle")
    suspend fun sendBattleHistory(@Body body: BattleHistoryRequest): Response<CommonResponse>

    @GET("battle")
    suspend fun getAllBattleHistory(): Response<BattleHistoryResponse>

    @PUT("unit")
    suspend fun updateUnit(@Body body: GameUnit): Response<CommonResponse>

}