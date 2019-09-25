package com.starwars.android.dependencyinjection


import com.starwars.android.fragments.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeUnitDetailFragment(): UnitDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeGameActivity(): FragmentGame

    @ContributesAndroidInjector
    abstract fun contributeBattleHistoryFragment(): BattleHistoryFragment

    @ContributesAndroidInjector
    abstract fun contributeUpdateUnitFragment(): UpdateUnitFragment

}
