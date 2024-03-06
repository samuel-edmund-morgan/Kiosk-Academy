package com.morgandev.kioskacademy.data.WarriorsData

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "warriors")
data class WarriorDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val profilePicture: Bitmap,
    val rank: String,
    val nameUA: String,
    val nameENG: String,
    val fullNameUA: String,
    val fullNameENG: String,
    val departmentEmblem: Int,
    val dateBirth: Int,
    val dateDied: Int,
    val description: String,
    val photos: List<Bitmap>,
    val videos: List<Bitmap>
)