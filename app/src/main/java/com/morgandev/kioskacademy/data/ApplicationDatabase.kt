package com.morgandev.kioskacademy.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.morgandev.kioskacademy.data.AppData.AppDataDao
import com.morgandev.kioskacademy.data.AppData.AppDataDbModel
import com.morgandev.kioskacademy.data.WarriorsData.WarriorDbModel
import com.morgandev.kioskacademy.data.WarriorsData.WarriorListDao

@TypeConverters(ListStringTypeConverter::class)
@Database
    (entities = [AppDataDbModel::class, WarriorDbModel::class], version = 1, exportSchema = false)
abstract class ApplicationDatabase: RoomDatabase() {
    abstract fun warriorListDao(): WarriorListDao
    abstract fun appDataDao(): AppDataDao

    companion object {

        private var INSTANCE: ApplicationDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "kiosk_academy.db"

        fun getInstance(application: Application): ApplicationDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    ApplicationDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = db
                return db
            }
        }
    }
}