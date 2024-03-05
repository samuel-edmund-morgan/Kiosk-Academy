package com.morgandev.kioskacademy.data

import androidx.room.TypeConverter

class ListByteArrayTypeConverter {
    @TypeConverter
    fun fromListIntToString(intList: List<ByteArray?>): String = intList.toString()
    @TypeConverter
    fun toListIntFromString(stringList: String): List<ByteArray?> {
        val result = ArrayList<ByteArray?>()
        val split = stringList.replace("[","").replace("]","")
            .replace(" ","").split(",")
        for (n in split) {
            try {
                result.add(n.toByteArray())
            } catch (e: Exception) {
                TODO("Not yet implemented")
            }
        }
        return result
    }

}