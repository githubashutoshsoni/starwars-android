package com.starwars.android.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.starwars.android.Constants
import com.starwars.android.data.room.dao.GameDAO
import com.starwars.android.data.room.models.BattleHistory
import com.starwars.android.data.room.models.GameUnit

/**
 * The Room database for this app
 */
@Database(entities = [GameUnit::class, BattleHistory::class],
        version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getGameDao(): GameDAO


    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, Constants.DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }
}
