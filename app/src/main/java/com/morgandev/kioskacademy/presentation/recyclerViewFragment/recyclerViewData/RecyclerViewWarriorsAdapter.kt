package com.morgandev.kioskacademy.presentation.recyclerViewFragment.recyclerViewData

import android.media.ThumbnailUtils
import android.os.CancellationSignal
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.morgandev.kioskacademy.databinding.FragmentRecyclerViewWarriorsAddBinding
import com.morgandev.kioskacademy.databinding.FragmentRecyclerViewWarriorsBinding
import com.morgandev.kioskacademy.databinding.WarriorItemFullBinding
import com.morgandev.kioskacademy.domain.entities.VideoData
import com.morgandev.kioskacademy.domain.entities.Warrior
import java.io.File


class RecyclerViewWarriorsAdapter :
    ListAdapter<VideoData, RecyclerViewWarriorsViewHolder>(RecyclerViewWarriorDiffCallback()) {


    var onWarriorClickListener: ((String, Int) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewWarriorsViewHolder {
        val bind = WarriorItemFullBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return RecyclerViewWarriorsViewHolder(bind)
    }
    override fun onBindViewHolder(viewHolder: RecyclerViewWarriorsViewHolder, position: Int) {
        val videoItem = getItem(position)
        val binding = viewHolder.binding
        val contextValue = viewHolder.itemView.context

        val videoFileNameValue = videoItem.videoFileName
        val videoFilePath = contextValue.filesDir

        val videoFile = File("${videoFilePath}/${videoFileNameValue}/${videoFileNameValue}")
        val size = android.util.Size(256, 256)
        val cs = CancellationSignal()
        val thumbnail = ThumbnailUtils.createVideoThumbnail(videoFile, size, cs)

        Glide.with(contextValue)
            .load(thumbnail)
            .into(binding.warriorIv)
            //.waitForLayout()



        binding.warriorTvName.text = videoItem.videoName.replace(". ", ".\n")
            //.replace('.', '\n')

        binding.root.setOnClickListener {
            onWarriorClickListener?.invoke(videoFileNameValue, position)
        }

    }


    companion object{
        const val VIEW_TYPE_ENABLED = 100
        const val MAX_POOL_SIZE = 30
    }


}