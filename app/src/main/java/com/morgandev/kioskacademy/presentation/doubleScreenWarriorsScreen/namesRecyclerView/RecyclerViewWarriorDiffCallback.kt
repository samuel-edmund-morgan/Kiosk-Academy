package com.morgandev.kioskacademy.presentation.doubleScreenWarriorsScreen.namesRecyclerView

import androidx.recyclerview.widget.DiffUtil
import com.morgandev.kioskacademy.domain.entities.Warrior

class RecyclerViewWarriorDiffCallback: DiffUtil.ItemCallback<Warrior>() {
    override fun areItemsTheSame(oldItem: Warrior, newItem: Warrior): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Warrior, newItem: Warrior): Boolean {
        return oldItem == newItem
    }
}