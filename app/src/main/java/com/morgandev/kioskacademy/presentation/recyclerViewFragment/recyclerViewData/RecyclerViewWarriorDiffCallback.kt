package com.morgandev.kioskacademy.presentation.recyclerViewFragment.recyclerViewData

import androidx.recyclerview.widget.DiffUtil
import com.morgandev.kioskacademy.domain.entities.VideoData
import com.morgandev.kioskacademy.domain.entities.Warrior

class RecyclerViewWarriorDiffCallback: DiffUtil.ItemCallback<VideoData>() {
    override fun areItemsTheSame(oldItem: VideoData, newItem: VideoData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: VideoData, newItem: VideoData): Boolean {
        return oldItem == newItem
    }
}