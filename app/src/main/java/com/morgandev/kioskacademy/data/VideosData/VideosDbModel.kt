package com.morgandev.kioskacademy.data.VideosData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "videos")
data class VideosDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val videoName: String,
    val videoFileName: String,
)