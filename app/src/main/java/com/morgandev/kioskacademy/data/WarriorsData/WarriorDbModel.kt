package com.morgandev.kioskacademy.data.WarriorsData

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "warriors")
data class WarriorDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val profilePicture: String,
    @ColumnInfo(defaultValue = "")
    val rank: String,
    val nameUA: String,
    val fullNameUA: String,
    @ColumnInfo(defaultValue = "")
    val departmentEmblem: String,
    val dateBirth: String,
    val dateDied: String,
    val description: String,
    val photos: List<String>,
    @ColumnInfo(defaultValue = "")
    val videos: List<String>
)