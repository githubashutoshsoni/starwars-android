package com.starwars.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.starwars.android.data.api.Result
import com.starwars.android.databinding.FragmentUnitDetailBinding
import com.starwars.android.dependencyinjection.Injectable
import com.starwars.android.dependencyinjection.injectViewModel
import com.starwars.android.hide
import com.starwars.android.show
import com.starwars.android.viewmodel.GameUnitDetailViewModel
import javax.inject.Inject

class UnitDetailFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: GameUnitDetailViewModel

    private val args: UnitDetailFragmentArgs by navArgs()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentUnitDetailBinding.inflate(inflater, container, false)

        viewModel = injectViewModel(viewModelFactory)
        viewModel.id = args.unitId!!

        subscribeUi(binding)

        return binding.root
    }

    private fun subscribeUi(binding: FragmentUnitDetailBinding) {
        viewModel.gameUnit.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.progressBar.hide()
                    binding.gameUnit = result.data
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