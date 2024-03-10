package com.morgandev.kioskacademy.data.AppData

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_data")
data class AppDataDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val backgroundImage: String,
    val emblemId: String,
    val year: String,
    val galleryNameUA: String,
) {
}