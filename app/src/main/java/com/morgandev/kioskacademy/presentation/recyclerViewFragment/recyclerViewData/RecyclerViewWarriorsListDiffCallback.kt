package com.morgandev.kioskacademy.presentation.recyclerViewFragment.recyclerViewData

import androidx.recyclerview.widget.DiffUtil
import com.morgandev.kioskacademy.domain.entities.VideoData
import com.morgandev.kioskacademy.domain.entities.Warrior

class RecyclerViewWarriorsListDiffCallback(
    private val oldList: List<VideoData>,
    private val newList: List<VideoData>,
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