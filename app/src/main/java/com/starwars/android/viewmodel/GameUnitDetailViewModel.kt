package com.starwars.android.viewmodel

import androidx.lifecycle.ViewModel
import com.starwars.android.data.repository.GameRepository
import com.starwars.android.data.room.models.GameUnit
import javax.inject.Inject

class GameUnitDetailViewModel @Inject constructor(repository: GameRepository) : ViewModel() {

    lateinit var id: String

    lateinit var updatedGameUnit: GameUnit

    val gameUnit by lazy { repository.gameUnit(id) }

    val updateGameUnit by lazy { repository.updateGameUnit(updatedGameUnit) }

}