package com.morgandev.kioskacademy.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream


class ListByteArrayTypeConverter {
    @TypeConverter
    fun fromListBitmapToString(bitmapList: List<Bitmap>): String = bitmapList.toString()

    @TypeConverter
    fun toListBitmapFromString(stringList: String): List<Bitmap> {
        val result = ArrayList<Bitmap>()
        val split = stringList.replace("[","").replace("]","")
            .replace(" ","").split(",")
        for (n in split) {
            try {
                val imageBytes = Base64.decode(n, 0)
                val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                result.add(image)
            } catch (e: Exception) {
                TODO("Not yet implemented")
            }
        }
        return result
    }


    @TypeConverter
    fun toBitmap(bytes: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    @TypeConverter
    fun fromBitmap(bmp: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

}