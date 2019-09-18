package com.starwars.android.dependencyinjection


import com.starwars.android.fragments.BattleHistoryFragment
import com.starwars.android.fragments.FragmentGame
import com.starwars.android.fragments.HomeFragment
import com.starwars.android.fragments.UnitDetailFragment
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

}
