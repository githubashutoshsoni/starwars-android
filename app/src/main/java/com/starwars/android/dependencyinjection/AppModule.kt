package com.starwars.android.dependencyinjection

import android.app.Application
import com.starwars.android.Constants
import com.starwars.android.data.api.GameRemoteDataSource
import com.starwars.android.data.api.GameService
import com.starwars.android.data.room.AppDatabase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, CoreDataModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideGameService(@GameApi okhttpClient: OkHttpClient,
                           converterFactory: GsonConverterFactory
    ) = provideService(okhttpClient, converterFactory, GameService::class.java)

    @Singleton
    @Provides
    fun provideGameSetRemoteDataSource(gameService: GameService) = GameRemoteDataSource(gameService)


    @GameApi
    @Provides
    fun providePrivateOkHttpClient(
            upstreamClient: OkHttpClient
    ): OkHttpClient {
        return upstreamClient.newBuilder().build()
    }

    @Singleton
    @Provides
    fun provideDb(app: Application) = AppDatabase.getInstance(app)

    @Singleton
    @Provides
    fun provideGameSetDao(db: AppDatabase) = db.getGameDao()

    @CoroutineScropeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)


    private fun createRetrofit(
            okhttpClient: OkHttpClient,
            converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okhttpClient)
                .addConverterFactory(converterFactory)
                .build()
    }

    private fun <T> provideService(okhttpClient: OkHttpClient,
                                   converterFactory: GsonConverterFactory, clazz: Class<T>): T {
        return createRetrofit(okhttpClient, converterFactory).create(clazz)
    }
}
