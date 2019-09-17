package com.starwars.android.viewmodel

import androidx.lifecycle.ViewModel
import com.starwars.android.data.repository.GameRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(repository: GameRepository) : ViewModel() {

    val gameUnits = repository.allGameUnits

}
