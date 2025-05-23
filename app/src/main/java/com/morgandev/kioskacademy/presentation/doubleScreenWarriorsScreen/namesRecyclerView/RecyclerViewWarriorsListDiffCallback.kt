package com.morgandev.kioskacademy.presentation.doubleScreenWarriorsScreen.namesRecyclerView

import androidx.recyclerview.widget.DiffUtil
import com.morgandev.kioskacademy.domain.entities.Warrior

class RecyclerViewWarriorsListDiffCallback(
    private val oldList: List<Warrior>,
    private val newList: List<Warrior>,
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }


}