package com.starwars.android.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.starwars.android.data.room.models.GameUnit

@Dao
interface GameDAO {

    @Insert(onConflict = REPLACE)
    fun insert(gameUnit: GameUnit)

    @Query("SELECT * FROM units ORDER BY _id")
    fun getAllGameUnits(): LiveData<List<GameUnit>>

    @Query("SELECT * FROM units WHERE _id = :id")
    fun getGameUnit(id: String): LiveData<GameUnit>

    @Insert(onConflict = REPLACE)
    fun insertMany(gameUnit: List<GameUnit>)

}