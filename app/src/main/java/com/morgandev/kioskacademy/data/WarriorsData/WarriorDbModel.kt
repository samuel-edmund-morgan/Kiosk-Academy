package com.morgandev.kioskacademy.data.WarriorsData

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "warriors")
data class WarriorDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    //@ColumnInfo(defaultValue = "hello")
    //make string
    val profilePicture: Bitmap,
    //@ColumnInfo(defaultValue = "hello")
    //make string
    val rank: String,
    val nameUA: String,
    val nameENG: String,
    val fullNameUA: String,
    val fullNameENG: String,
    //@ColumnInfo(defaultValue = "hello")
    //make string
    val departmentEmblem: Int,
    val dateBirth: Int,
    val dateDied: Int,
    val description: String,
    //@ColumnInfo(defaultValue = "hello")
    //make string
    val photos: List<Bitmap>,
    //@ColumnInfo(defaultValue = "hello")
    //make string
    val videos: List<Bitmap>
)