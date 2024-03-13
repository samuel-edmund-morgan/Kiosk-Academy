package com.morgandev.kioskacademy.presentation.doubleScreenWarriorsScreen.videoGalleryRecyclerView

import androidx.recyclerview.widget.DiffUtil

class VideoGalleryRecyclerViewListDiffCallback(
    private val oldList: List<String>,
    private val newList: List<String>,
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
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }


}