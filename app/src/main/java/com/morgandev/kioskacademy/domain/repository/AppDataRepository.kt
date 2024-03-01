package com.morgandev.kioskacademy.domain.repository

import androidx.lifecycle.LiveData
import com.morgandev.kioskacademy.domain.entity.AppData
import com.morgandev.kioskacademy.domain.entity.Warrior
import java.util.UUID

interface AppDataRepository {
    suspend fun addAppData(appData: AppData)
    suspend fun deleteAppData(appData: AppData)
    suspend fun editAppData(appData: AppData)
    suspend fun getAppData(appDataId: Int): AppData
    fun getAppDataList(): LiveData<List<AppData>>
}