package com.morgandev.kioskacademy.domain.entities


data class Warrior(
    val profilePicture: ByteArray?,
    val rank: String,
    val nameUA: String,
    val nameENG: String,
    val fullNameUA: String,
    val fullNameENG: String,
    val departmentEmblem: Int,
    val dateBirth: Int,
    val dateDied: Int,
    val description: String,
    val photos: List<ByteArray?>,
    val videos: List<ByteArray?>,
    val id: Int = UNDEFINED_ID,
) {
    companion object{
        const val UNDEFINED_ID = 0
    }
}