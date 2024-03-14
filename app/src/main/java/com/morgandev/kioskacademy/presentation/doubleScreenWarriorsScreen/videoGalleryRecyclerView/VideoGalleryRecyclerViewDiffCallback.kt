package com.morgandev.kioskacademy.presentation.doubleScreenWarriorsScreen.videoGalleryRecyclerView

import androidx.recyclerview.widget.DiffUtil

class VideoGalleryRecyclerViewDiffCallback: DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}