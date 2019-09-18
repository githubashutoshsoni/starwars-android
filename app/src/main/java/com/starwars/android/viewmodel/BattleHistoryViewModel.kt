package com.starwars.android.viewmodel

import androidx.lifecycle.ViewModel
import com.starwars.android.data.api.models.BattleHistoryRequest
import com.starwars.android.data.repository.GameRepository
import javax.inject.Inject

class BattleHistoryViewModel @Inject constructor(repository: GameRepository) : ViewModel() {

    lateinit var battleHistoryRequest: BattleHistoryRequest

    val sendBattleHistory by lazy { repository.sendBattleHistory(battleHistoryRequest) }

    val allBattleHistory by lazy { repository.getAllBattleHistory() }

}