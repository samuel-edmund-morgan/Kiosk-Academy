package com.morgandev.kioskacademy.domain.usecases.VideosUseCase

import androidx.lifecycle.LiveData
import com.morgandev.kioskacademy.domain.entities.VideoData

import com.morgandev.kioskacademy.domain.repositories.VideosRepository


class GetVideosListUseCase(private val videosRepository: VideosRepository) {
    fun getVideosList(): LiveData<List<VideoData>>{
        return videosRepository.getVideoList()
    }
}