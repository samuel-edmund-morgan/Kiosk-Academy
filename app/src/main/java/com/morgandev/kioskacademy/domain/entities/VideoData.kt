package com.morgandev.kioskacademy.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class VideoData(
    val videoName: String,
    val videoFileName: String,
    val id: Int = UNDEFINED_ID,
) : Parcelable
{
    companion object{
        const val UNDEFINED_ID = 0
    }
}