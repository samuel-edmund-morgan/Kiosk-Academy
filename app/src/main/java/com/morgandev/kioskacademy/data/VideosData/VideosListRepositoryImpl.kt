package com.morgandev.kioskacademy.data.VideosData

import android.app.Application
import androidx.lifecycle.LiveData
import com.morgandev.kioskacademy.domain.entities.Warrior
import com.morgandev.kioskacademy.domain.repositories.WarriorsRepository
import androidx.lifecycle.map
import com.morgandev.kioskacademy.data.ApplicationDatabase
import com.morgandev.kioskacademy.domain.entities.VideoData
import com.morgandev.kioskacademy.domain.repositories.VideosRepository


class VideosListRepositoryImpl(
    application: Application
): VideosRepository {

    private val videosListDao = ApplicationDatabase.getInstance(application).videosListDao()
    private val mapper = VideosListMapper()
    override suspend fun addVideo(video: VideoData) {
        videosListDao.addVideo(mapper.mapEntityToDbModel(video))
    }

    override suspend fun deleteVideo(video: VideoData) {
        videosListDao.deleteVideo(video.id)
    }

    override suspend fun editVideo(video: VideoData) {
        videosListDao.addVideo(mapper.mapEntityToDbModel(video))
    }

    override suspend fun getVideo(videoId: Int): VideoData {
        val dbModel = videosListDao.getVideo(videoId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getVideoList(): LiveData<List<VideoData>> = videosListDao.getVideosList().map {
        mapper.mapListDbModelToListEntity(it)
    }


}