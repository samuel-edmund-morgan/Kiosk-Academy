package com.morgandev.kioskacademy.presentation.doubleScreenWarriorsScreen.videoGalleryRecyclerView

import android.media.ThumbnailUtils
import android.os.CancellationSignal
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.MediaController
import androidx.compose.ui.geometry.Size
import androidx.core.net.toUri
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.morgandev.kioskacademy.databinding.VideoGalleryItemBinding
import java.io.File


class VideoGalleryRecyclerViewAdapter(private val warriorDir: String) :
    ListAdapter<String, VideoGalleryRecyclerViewViewHolder>(VideoGalleryRecyclerViewDiffCallback()) {


    var onVideoClickListener: ((String, Int) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VideoGalleryRecyclerViewViewHolder {
        val bind = VideoGalleryItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return VideoGalleryRecyclerViewViewHolder(bind)
    }
    override fun onBindViewHolder(viewHolder: VideoGalleryRecyclerViewViewHolder, position: Int) {
        val videoItem = getItem(position)
        val binding = viewHolder.binding
        val contextValue = viewHolder.itemView.context

        val profilePictureValue = warriorDir
        val warriorVvValue = binding.galleryVideoVv
        val videoFilePath = contextValue.filesDir

        warriorVvValue.setVideoURI(File("${videoFilePath}/${profilePictureValue}/${videoItem}").toUri())
        //warriorVvValue.setMediaController(MediaController(contextValue))


        binding.root.setOnClickListener {
            onVideoClickListener?.invoke(videoItem, position)
        }

        val videoFile = File("${videoFilePath}/${profilePictureValue}/${videoItem}")
        val size = android.util.Size(256, 256)
        val cs = CancellationSignal()
        val thumbnail = ThumbnailUtils.createVideoThumbnail(videoFile, size, cs)

        Glide.with(contextValue)
            .load(thumbnail)
            .into(binding.thumbnailIv)
            .waitForLayout()



    }

    companion object{
        const val VIEW_TYPE_ENABLED = 100
        const val MAX_POOL_SIZE = 30
    }


}