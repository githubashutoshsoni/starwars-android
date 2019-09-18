package com.starwars.android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.starwars.android.data.room.models.BattleHistory
import com.starwars.android.databinding.RecyclerItemBattleResultBinding

class BattleHistoryResultsAdapter : ListAdapter<BattleHistory, BattleHistoryResultsAdapter.ViewHolder>(DiffCallback2()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val battleHistory = getItem(position)
        holder.apply {
            bind(battleHistory)
            itemView.tag = battleHistory._id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerItemBattleResultBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    class ViewHolder(
            private val binding: RecyclerItemBattleResultBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BattleHistory) {
            binding.apply {
                battleHistory = item
                if (item.winner == "DROID_ARMY") {
                    resultTextView.text = "Droid army won the battle"
                } else {
                    resultTextView.text = "Clone troopers won the battle"
                }
                executePendingBindings()
            }
        }
    }

}

private class DiffCallback2 : DiffUtil.ItemCallback<BattleHistory>() {

    override fun areItemsTheSame(oldItem: BattleHistory, newItem: BattleHistory): Boolean {
        return oldItem._id == newItem._id
    }

    override fun areContentsTheSame(oldItem: BattleHistory, newItem: BattleHistory): Boolean {
        return oldItem == newItem
    }

}