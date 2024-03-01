package com.morgandev.kioskacademy.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppDataDao {
    @Query("SELECT * FROM app_data")
    fun getAppDataList(): LiveData<List<AppDataDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAppData(appDataDbModel: AppDataDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAppDataSync(appDataDbModel: AppDataDbModel)

    @Query("DELETE FROM app_data WHERE id=:appDataId")
    suspend fun deleteAppData(appDataId: Int)

    @Query("DELETE FROM app_data WHERE id=:appDataId")
    fun deleteAppDataSync(appDataId: Int)

    @Query("SELECT * FROM app_data WHERE id=:appDataId LIMIT 1")
    suspend fun getAppData(appDataId: Int): AppDataDbModel
}