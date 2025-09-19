package com.morgandev.kioskacademy.presentation.doubleScreenWarriorsScreen.videoGalleryRecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.morgandev.kioskacademy.databinding.VideoGalleryItemBinding
import java.io.File


class VideoGalleryRecyclerViewAdapter(initialWarriorDir: String) :
    ListAdapter<String, VideoGalleryRecyclerViewViewHolder>(VideoGalleryRecyclerViewDiffCallback()) {

    init { setHasStableIds(true) }

    private var warriorDir: String = initialWarriorDir
    fun updateWarriorDir(newDir: String) { warriorDir = newDir }

    var onVideoClickListener: ((String, Int) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VideoGalleryRecyclerViewViewHolder {
        val bind = VideoGalleryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoGalleryRecyclerViewViewHolder(bind)
    }

    override fun getItemId(position: Int): Long = (warriorDir.hashCode() * 37L + getItem(position).hashCode())

    override fun onBindViewHolder(viewHolder: VideoGalleryRecyclerViewViewHolder, position: Int) {
        val videoItem = getItem(position)
        val binding = viewHolder.binding
        val contextValue = viewHolder.itemView.context
        val basePath = contextValue.filesDir
        val file = File("${basePath}/${warriorDir}/${videoItem}")

        // Load a frame (0.5s) as thumbnail off main thread via Glide
        Glide.with(contextValue)
            .asBitmap()
            .load(file)
            .frame(500_000) // microseconds
            .transform(CenterCrop())
            .override(153, 153)
            .into(binding.thumbnailIv)

        // Hide heavy inline playback VideoView to avoid measure/layout costs
        binding.galleryVideoVv.visibility = android.view.View.GONE

        binding.root.setOnClickListener {
            onVideoClickListener?.invoke(videoItem, position)
        }
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 100
        const val MAX_POOL_SIZE = 30
    }

}