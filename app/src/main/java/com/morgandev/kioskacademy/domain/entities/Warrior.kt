package com.morgandev.kioskacademy.domain.entities

import android.graphics.Bitmap


data class Warrior(
    val profilePicture: String,
    val rank: String,
    val nameUA: String,
    val fullNameUA: String,
    val departmentEmblem: String,
    val dateBirth: String,
    val dateDied: String,
    val description: String,
    val photos: String,
    val videos: String,
    val id: Int = UNDEFINED_ID,
) {
    companion object{
        const val UNDEFINED_ID = 0
    }
}