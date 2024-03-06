package com.morgandev.kioskacademy.domain.entities

import android.graphics.Bitmap


data class Warrior(
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
    val videos: List<Bitmap>,
    val id: Int = UNDEFINED_ID,
) {
    companion object{
        const val UNDEFINED_ID = 0
    }
}