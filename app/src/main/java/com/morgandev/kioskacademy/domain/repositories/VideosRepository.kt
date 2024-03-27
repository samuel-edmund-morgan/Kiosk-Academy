package com.morgandev.kioskacademy.domain.repositories

import androidx.lifecycle.LiveData
import com.morgandev.kioskacademy.domain.entities.VideoData

interface VideosRepository {
    suspend fun addVideo(video: VideoData)
    suspend fun deleteVideo(video: VideoData)
    suspend fun editVideo(video: VideoData)
    suspend fun getVideo(videoId: Int): VideoData
    fun getVideoList(): LiveData<List<VideoData>>
}