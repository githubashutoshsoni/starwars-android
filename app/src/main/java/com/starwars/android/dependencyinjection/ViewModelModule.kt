package com.starwars.android.dependencyinjection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.starwars.android.viewmodel.GameUnitDetailViewModel
import com.starwars.android.viewmodel.HomeViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GameUnitDetailViewModel::class)
    abstract fun bindGameViewModel(viewModel: GameUnitDetailViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}
