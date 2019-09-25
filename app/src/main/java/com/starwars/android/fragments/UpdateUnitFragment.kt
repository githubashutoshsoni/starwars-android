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
import com.starwars.android.data.room.models.GameUnit
import com.starwars.android.databinding.FragmentUpdateUnitBinding
import com.starwars.android.dependencyinjection.Injectable
import com.starwars.android.dependencyinjection.injectViewModel
import com.starwars.android.hide
import com.starwars.android.show
import com.starwars.android.toast
import com.starwars.android.viewmodel.GameUnitDetailViewModel
import timber.log.Timber
import javax.inject.Inject

class UpdateUnitFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: GameUnitDetailViewModel

    private val args: UpdateUnitFragmentArgs by navArgs()

    private lateinit var dBinding: FragmentUpdateUnitBinding

    private var gameUnit: GameUnit? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentUpdateUnitBinding.inflate(inflater, container, false)
        dBinding = binding

        viewModel = injectViewModel(viewModelFactory)
        viewModel.id = args.unitId!!

        subscribeUi(binding)

        binding.clickListener = View.OnClickListener {
            updateUnit()
        }

        return binding.root
    }

    private fun subscribeUi(binding: FragmentUpdateUnitBinding) {
        viewModel.gameUnit.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.progressBar.hide()
                    binding.gameUnit = result.data
                    gameUnit = result.data
                }
                Result.Status.LOADING -> binding.progressBar.show()
                Result.Status.ERROR -> {
                    binding.progressBar.hide()
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun updateUnit() {
        if (valid()) {
            gameUnit?.let {
                viewModel.updatedGameUnit = GameUnit(it._id,
                        dBinding.editTextUnitName.text.toString(),
                        dBinding.editTextAgility.text.toString().toInt(),
                        dBinding.unitDescription.text.toString(),
                        dBinding.editTextIntelligence.text.toString().toInt(),
                        dBinding.editTextStrength.text.toString().toInt(),
                        it.type,
                        null,
                        null,
                        null
                )
            }
            viewModel.updateGameUnit.observe(viewLifecycleOwner, Observer { result ->
                Timber.e(result.data.toString())
            })
        }
    }

    private fun valid(): Boolean {
        if (dBinding.editTextUnitName.text.isNullOrEmpty()) {
            context?.toast("Unit name cannot be empty")
            return false
        }
        if (dBinding.editTextStrength.text.isNullOrEmpty()) {
            context?.toast("Strength cannot be empty")
            return false
        }
        if (dBinding.editTextAgility.text.isNullOrEmpty()) {
            context?.toast("Agility cannot be empty")
            return false
        }
        if (dBinding.editTextIntelligence.text.isNullOrEmpty()) {
            context?.toast("Intelligence cannot be empty")
            return false
        }
        return true
    }
}