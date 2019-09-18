package com.starwars.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.starwars.android.adapters.BattleHistoryResultsAdapter
import com.starwars.android.data.api.Result
import com.starwars.android.databinding.FragmentBattleHistoryBinding
import com.starwars.android.dependencyinjection.Injectable
import com.starwars.android.dependencyinjection.injectViewModel
import com.starwars.android.hide
import com.starwars.android.show
import com.starwars.android.viewmodel.BattleHistoryViewModel
import javax.inject.Inject

class BattleHistoryFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var battleHistoryViewModel: BattleHistoryViewModel
    val adapter = BattleHistoryResultsAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBattleHistoryBinding.inflate(inflater, container, false)

        binding.recyclerView.adapter = adapter
        battleHistoryViewModel = injectViewModel(viewModelFactory)

        subscribeUi(binding)

        return binding.root
    }


    private fun subscribeUi(binding: FragmentBattleHistoryBinding) {
        battleHistoryViewModel.allBattleHistory.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.progressBar.hide()
                    result.data.let { adapter.submitList(it) }
                }
                Result.Status.LOADING -> binding.progressBar.show()
                Result.Status.ERROR -> {
                    binding.progressBar.hide()
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

}