package com.morgandev.kioskacademy.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_data")
data class AppDataDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val backgroundImage: ByteArray,
    val emblemId: Int,
    val year: Int,
    val galleryNameUA: String,
    val galleryNameENG: String
) {
}