package com.morgandev.kioskacademy.domain.repository

import androidx.lifecycle.LiveData
import com.morgandev.kioskacademy.domain.entity.AppData
import com.morgandev.kioskacademy.domain.entity.Warrior
import java.util.UUID

interface AppDataRepository {
    fun addAppData(appData: AppData)
    fun deleteAppData(appData: AppData)
    fun editAppData(appData: AppData)
    fun getAppData(appDataId: Int): AppData
    fun getAppDataList(): LiveData<List<AppData>>
}