package com.morgandev.kioskacademy.presentation.doubleScreenWarriorsScreen.photoGalleryRecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.morgandev.kioskacademy.databinding.PhotoGalleryItemBinding
import java.io.File

class PhotoGalleryRecyclerViewAdapter(initialWarriorDir: String) :
    ListAdapter<String, PhotoGalleryRecyclerViewViewHolder>(PhotoGalleryRecyclerViewDiffCallback()) {

    init { setHasStableIds(true) }

    private var warriorDir: String = initialWarriorDir
    fun setWarriorDir(newDir: String) { warriorDir = newDir }

    var onPhotoClickListener: ((String, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoGalleryRecyclerViewViewHolder {
        val bind = PhotoGalleryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoGalleryRecyclerViewViewHolder(bind)
    }

    override fun getItemId(position: Int): Long = (warriorDir.hashCode() * 31L + getItem(position).hashCode())

    override fun onBindViewHolder(viewHolder: PhotoGalleryRecyclerViewViewHolder, position: Int) {
        val photoItem = getItem(position)
        val binding = viewHolder.binding
        val contextValue = viewHolder.itemView.context
        val picFilePath = contextValue.filesDir

        Glide.with(contextValue)
            .load(File("${picFilePath}/${warriorDir}/${photoItem}"))
            .centerCrop()
            .override(153, 153)
            .into(binding.galleryImageIv)

        binding.root.setOnClickListener { onPhotoClickListener?.invoke(photoItem, position) }
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 100
        const val MAX_POOL_SIZE = 30
    }
}
