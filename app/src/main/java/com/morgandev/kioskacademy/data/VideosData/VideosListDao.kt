package com.morgandev.kioskacademy.data.VideosData

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VideosListDao {
    @Query("SELECT * FROM videos")
    fun getVideosList(): LiveData<List<VideosDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addVideo(videosDbModel: VideosDbModel)

    @Query("DELETE FROM videos WHERE id=:videoId")
    suspend fun deleteVideo(videoId: Int)

    @Query("SELECT * FROM videos WHERE id=:videoId LIMIT 1")
    suspend fun getVideo(videoId: Int): VideosDbModel


}