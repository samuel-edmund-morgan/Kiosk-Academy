package com.morgandev.kioskacademy.domain.repositories

import androidx.lifecycle.LiveData
import com.morgandev.kioskacademy.domain.entities.AppData

interface AppDataRepository {
    suspend fun addAppData(appData: AppData)
    suspend fun deleteAppData(appData: AppData)
    suspend fun editAppData(appData: AppData)
    suspend fun getAppData(appDataId: Int): AppData
    fun getAppDataList(): LiveData<List<AppData>>
}