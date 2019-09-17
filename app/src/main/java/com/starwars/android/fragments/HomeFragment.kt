package com.starwars.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.starwars.android.adapters.GameUnitsAdapter
import com.starwars.android.data.api.Result
import com.starwars.android.databinding.FragmentHomeBinding
import com.starwars.android.dependencyinjection.Injectable
import com.starwars.android.dependencyinjection.injectViewModel
import com.starwars.android.hide
import com.starwars.android.show
import com.starwars.android.viewmodel.HomeViewModel
import javax.inject.Inject

class HomeFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: HomeViewModel
    val adapter = GameUnitsAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        viewModel = injectViewModel(viewModelFactory)

        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        context ?: return binding.root

        binding.recyclerView.adapter = adapter

        subscribeUi(binding)

        binding.fab.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToFragmentGame()
            findNavController().navigate(direction)
        }

        return binding.root
    }

    private fun subscribeUi(binding: FragmentHomeBinding) {
        viewModel.gameUnits.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.progressBar.hide()
                    result.data?.let { adapter.submitList(it) }
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