package com.starwars.android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.starwars.android.data.room.models.GameUnit
import com.starwars.android.databinding.RecyclerCharactersItemBinding
import com.starwars.android.fragments.HomeFragmentDirections

class GameUnitsAdapter : ListAdapter<GameUnit, GameUnitsAdapter.ViewHolder>(DiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gameUnit = getItem(position)
        holder.apply {
            bind(createOnClickListener(gameUnit._id), gameUnit)
            itemView.tag = gameUnit._id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerCharactersItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    private fun createOnClickListener(id: String): View.OnClickListener {
        return View.OnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToUnitDetailFragment(id)
            it.findNavController().navigate(direction)
        }
    }

    class ViewHolder(
            private val binding: RecyclerCharactersItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: GameUnit) {
            binding.apply {
                clickListener = listener
                gameUnit = item
                executePendingBindings()
            }
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<GameUnit>() {

    override fun areItemsTheSame(oldItem: GameUnit, newItem: GameUnit): Boolean {
        return oldItem._id == newItem._id
    }

    override fun areContentsTheSame(oldItem: GameUnit, newItem: GameUnit): Boolean {
        return oldItem == newItem
    }

}