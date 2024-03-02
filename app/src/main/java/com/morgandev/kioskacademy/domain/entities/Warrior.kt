package com.morgandev.kioskacademy.domain.entities


data class Warrior(
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
) {
    companion object{
        const val UNDEFINED_ID = -1
    }
}