package com.morgandev.kioskacademy.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "warriors")
data class WarriorDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val profilePicture: Int,
    val rank: String,
    val nameUA: String,
    val nameENG: String,
    val fullNameUA: String,
    val fullNameENG: String,
    val departmentEmblem: Int,
    val dateBirth: Int,
    val dateDied: Int,
    val description: String,
    val photos: List<Int>,
    val videos: List<Int>
)