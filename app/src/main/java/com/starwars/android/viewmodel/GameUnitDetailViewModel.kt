package com.starwars.android.viewmodel

import androidx.lifecycle.ViewModel
import com.starwars.android.data.repository.GameRepository
import javax.inject.Inject

class GameUnitDetailViewModel @Inject constructor(repository: GameRepository) : ViewModel() {

    lateinit var id: String

    val gameUnit by lazy { repository.gameUnit(id) }

}