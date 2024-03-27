package com.morgandev.kioskacademy.data.VideosData

import com.morgandev.kioskacademy.domain.entities.VideoData


class VideosListMapper {

    fun mapEntityToDbModel(videoData: VideoData) = VideosDbModel(
        id = videoData.id,
        videoName = videoData.videoName,
        videoFileName = videoData.videoFileName
    )

    fun mapDbModelToEntity(videosDbModel: VideosDbModel) = VideoData(
        id = videosDbModel.id,
        videoName = videosDbModel.videoName,
        videoFileName = videosDbModel.videoFileName
    )

    fun mapListDbModelToListEntity(list: List<VideosDbModel>) = list.map {
        mapDbModelToEntity(it)
    }

}