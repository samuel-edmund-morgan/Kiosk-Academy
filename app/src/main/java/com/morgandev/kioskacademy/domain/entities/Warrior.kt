package com.morgandev.kioskacademy.domain.entities

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Warrior(
    val profilePicture: String,
    val profileDetailedPhoto: String,
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
) : Parcelable
{
    companion object{
        const val UNDEFINED_ID = 0
    }
}