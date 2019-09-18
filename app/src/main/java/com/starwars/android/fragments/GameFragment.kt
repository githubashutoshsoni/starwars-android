package com.starwars.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.starwars.android.data.api.Result
import com.starwars.android.data.api.models.BattleHistoryRequest
import com.starwars.android.data.room.models.GameUnit
import com.starwars.android.databinding.FragmentGameBinding
import com.starwars.android.dependencyinjection.Injectable
import com.starwars.android.dependencyinjection.injectViewModel
import com.starwars.android.toast
import com.starwars.android.viewmodel.BattleHistoryViewModel
import com.starwars.android.viewmodel.HomeViewModel
import timber.log.Timber
import javax.inject.Inject
import kotlin.random.Random


class FragmentGame : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: HomeViewModel
    private lateinit var battleHistoryViewModel: BattleHistoryViewModel

    var gameUnits = ArrayList<GameUnit>()

    // Droid army cannot exceed 600 troops
    val MAX_DROID_ARMY_LIMIT = 40

    // Max 2/3 possible limit. will be updated after droid population
    var MAX_CLONE_TROOPER_LIMIT = 400

    var DRIOD_ARMY_CAPACITY = 0
    var CLONE_TROOP_CAPACITY = 0

    // Seprated by troop type
    val droidArmyTroops = ArrayList<GameUnit>()
    val cloneArmyTroops = ArrayList<GameUnit>()

    // Gonna participate in battle
    val droidBattleTroops = ArrayList<BattleUnit>()
    val cloneBattleTroops = ArrayList<BattleUnit>()

    val warLog = ArrayList<String>()

    lateinit var dBinding: FragmentGameBinding

    var battleEnded = false

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentGameBinding.inflate(inflater, container, false)

        viewModel = injectViewModel(viewModelFactory)
        battleHistoryViewModel = injectViewModel(viewModelFactory)

        dBinding = binding

        dBinding.startBattle.setOnClickListener {
            if (!battleEnded) {
                startAttacking()
            } else {
                context?.toast("Battle ended")
                findNavController().navigateUp()
            }
        }

        bindData()

        getAllTroops()

        return binding.root
    }


    private fun getAllTroops() {
        viewModel.gameUnits.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    gameUnits.clear()
                    gameUnits.addAll(result.data!!.toList())
                    viewModel.gameUnits.removeObservers(viewLifecycleOwner)
                    randomizeTroopCount()
                }
            }
        })
    }

    // Reset armies
    private fun clearData() {
        cloneArmyTroops.clear()
        droidArmyTroops.clear()
        cloneBattleTroops.clear()
        droidBattleTroops.clear()
        warLog.clear()
    }

    private fun bindData() {
        dBinding.cloneArmyCount = CLONE_TROOP_CAPACITY
        dBinding.droidArmyCount = DRIOD_ARMY_CAPACITY

        dBinding.cloneArmyAliveCount = cloneBattleTroops.size
        dBinding.droidArmyAliveCount = droidBattleTroops.size
    }

    private fun randomizeTroopCount() {

        clearData()

        // Randomly generate droid army capacity
        DRIOD_ARMY_CAPACITY = Random.nextInt(1, MAX_DROID_ARMY_LIMIT)

        // Update clone troopers count using 2/3 rule
        MAX_CLONE_TROOPER_LIMIT = (2 * DRIOD_ARMY_CAPACITY) / 3

        if (MAX_CLONE_TROOPER_LIMIT == 0) MAX_CLONE_TROOPER_LIMIT = 2

        // using above value finally randomize clone troop army count
        CLONE_TROOP_CAPACITY = Random.nextInt(1, MAX_CLONE_TROOPER_LIMIT)

        Timber.e("droid army $DRIOD_ARMY_CAPACITY  clone army $CLONE_TROOP_CAPACITY")

        groupTroops()

    }

    // Group troops based on its type
    private fun groupTroops() {

        gameUnits.forEach {
            if (it.type == "ALLY") {
                cloneArmyTroops.add(it)
            } else if (it.type == "ENEMY") {
                droidArmyTroops.add(it)
            }
        }

        spawnTroops()

    }

    // Spawn troops based on the randomized army population.
    private fun spawnTroops() {

        for (i in 0..DRIOD_ARMY_CAPACITY) {
            val randomTroop = Random.nextInt(0, droidArmyTroops.size - 1)
            val troop = droidArmyTroops[randomTroop]
            droidBattleTroops.add(BattleUnit(i, troop.name, troop.strength.toDouble(), troop.agility.toDouble(), troop.intelligence.toDouble()))
        }

        for (i in 0..CLONE_TROOP_CAPACITY) {
            val randomTroop = Random.nextInt(0, cloneArmyTroops.size - 1)
            val troop = cloneArmyTroops[randomTroop]
            cloneBattleTroops.add(BattleUnit(i, troop.name, troop.strength.toDouble(), troop.agility.toDouble(), troop.intelligence.toDouble()))
        }

    }

    private fun startAttacking() {

        try {

            val cloneTroop = cloneBattleTroops.random()
            val droidTroop = droidBattleTroops.random()

            //let's take 30% of agility as  armour, armour gives immune to damage
            val cloneTroopArmour = cloneTroop.agility * 0.3
            val droidTroopArmour = cloneTroop.agility * 0.3

            // Deal damage to the enemy unit w.r.t armour
            cloneTroop.strength = cloneTroop.strength - (droidTroop.intelligence - cloneTroopArmour)
            droidTroop.strength = droidTroop.strength - (cloneTroop.intelligence - droidTroopArmour)


            warLog.add("${droidTroop.name} attacked ${cloneTroop.name} dealt damage of ${droidTroop.intelligence}")
            cloneBattleTroops[cloneTroop.troopId] = cloneTroop

            warLog.add("${cloneTroop.name} attacked ${droidTroop.name} dealt damage of ${cloneTroop.intelligence}")
            droidBattleTroops[droidTroop.troopId] = droidTroop

            removeDeadTroops()

        } catch (e: Exception) {

        }

        if (cloneBattleTroops.size == 0 || droidBattleTroops.size == 0) {
            var battleHistoryRequest: BattleHistoryRequest? = null
            battleEnded = true
            if (cloneBattleTroops.size > droidBattleTroops.size) {
                battleHistoryRequest = BattleHistoryRequest(CLONE_TROOP_CAPACITY, DRIOD_ARMY_CAPACITY, "CLONE_TROOPERS")
                context?.toast("Clone trooper won the battle")
            } else {
                battleHistoryRequest = BattleHistoryRequest(CLONE_TROOP_CAPACITY, DRIOD_ARMY_CAPACITY, "DROID_ARMY")
                context?.toast("Droid army won the battle")
            }
            battleHistoryViewModel.battleHistoryRequest = battleHistoryRequest
            battleHistoryViewModel.sendBattleHistory.observe(viewLifecycleOwner, Observer { result ->

            })
        }

        bindData()

    }

    // Remove dead troops i.e strength <= 0
    private fun removeDeadTroops() {

        var removeClone = -1
        var removeDroid = -1

        cloneBattleTroops.forEachIndexed { index, battleunit ->
            if (battleunit.strength <= 0) {
                removeClone = index
                warLog.add("${battleunit.name} was died in battle")
            }
        }

        if (removeClone >= 0) {
            cloneBattleTroops.removeAt(removeClone)
        }

        cloneBattleTroops.forEachIndexed { index, battleunit ->
            if (battleunit.strength <= 0) {
                removeDroid = index
                warLog.add("${battleunit.name} was died in battle")
            }
        }

        if (removeDroid >= 0) {
            droidBattleTroops.removeAt(removeDroid)
        }

    }


}

data class BattleUnit(
        val troopId: Int,
        val name: String,
        var strength: Double = 0.0,
        val agility: Double = 0.0,
        val intelligence: Double = 0.0
)


