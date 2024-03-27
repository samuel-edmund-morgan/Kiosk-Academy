package com.morgandev.kioskacademy.domain.usecases.VideosUseCase

import com.morgandev.kioskacademy.domain.entities.VideoData
import com.morgandev.kioskacademy.domain.entities.Warrior
import com.morgandev.kioskacademy.domain.repositories.VideosRepository
import com.morgandev.kioskacademy.domain.repositories.WarriorsRepository

class AddVideoUseCase(private val videosRepository: VideosRepository) {
    suspend fun addVideoData(video: VideoData){
        videosRepository.addVideo(video)
    }
}