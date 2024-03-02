package com.morgandev.kioskacademy.domain.entities

data class AppData(
    val id: Int,
    val backgroundImage: ByteArray?,
    val emblemId: Int,
    val year: Int,
    val galleryNameUA: String,
    val galleryNameENG: String
)