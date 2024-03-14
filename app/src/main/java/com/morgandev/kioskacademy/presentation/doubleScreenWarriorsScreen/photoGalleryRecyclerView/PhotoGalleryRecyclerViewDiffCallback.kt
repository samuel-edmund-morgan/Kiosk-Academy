package com.morgandev.kioskacademy.presentation.doubleScreenWarriorsScreen.photoGalleryRecyclerView

import androidx.recyclerview.widget.DiffUtil

class PhotoGalleryRecyclerViewDiffCallback: DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}