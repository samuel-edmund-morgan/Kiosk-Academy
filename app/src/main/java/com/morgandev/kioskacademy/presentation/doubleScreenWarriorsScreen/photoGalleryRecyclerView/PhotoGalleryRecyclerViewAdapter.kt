package com.morgandev.kioskacademy.presentation.doubleScreenWarriorsScreen.photoGalleryRecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.morgandev.kioskacademy.databinding.PhotoGalleryItemBinding
import com.morgandev.kioskacademy.databinding.WarriorItemNameBinding
import com.morgandev.kioskacademy.domain.entities.Warrior
import java.io.File


class PhotoGalleryRecyclerViewAdapter(private val warriorDir: String) :
    ListAdapter<String, PhotoGalleryRecyclerViewViewHolder>(PhotoGalleryRecyclerViewDiffCallback()) {


    var onPhotoClickListener: ((String, Int) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoGalleryRecyclerViewViewHolder {
        val bind = PhotoGalleryItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return PhotoGalleryRecyclerViewViewHolder(bind)
    }
    override fun onBindViewHolder(viewHolder: PhotoGalleryRecyclerViewViewHolder, position: Int) {
        val photoItem = getItem(position)
        val binding = viewHolder.binding
        val contextValue = viewHolder.itemView.context

        val profilePictureValue = warriorDir
        val warriorIvValue = binding.galleryImageIv
        val picFilePath = contextValue.filesDir


        Glide.with(contextValue)
            .load(File("${picFilePath}/${profilePictureValue}/${photoItem}"))
            .into(warriorIvValue)
            .waitForLayout()

        binding.root.setOnClickListener {

            onPhotoClickListener?.invoke(photoItem, position)
        }

    }

    companion object{
        const val VIEW_TYPE_ENABLED = 100
        const val MAX_POOL_SIZE = 30
    }


}