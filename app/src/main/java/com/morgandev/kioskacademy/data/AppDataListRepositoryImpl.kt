package com.morgandev.kioskacademy.data

import androidx.lifecycle.LiveData
import com.morgandev.kioskacademy.domain.entity.AppData
import com.morgandev.kioskacademy.domain.entity.Warrior
import com.morgandev.kioskacademy.domain.repository.AppDataRepository
import com.morgandev.kioskacademy.domain.repository.WarriorsRepository


class AppDataListRepositoryImpl(): AppDataRepository {
    override fun addAppData(appData: AppData) {
        TODO("Not yet implemented")
    }

    override fun deleteAppData(appData: AppData) {
        TODO("Not yet implemented")
    }

    override fun editAppData(appData: AppData) {
        TODO("Not yet implemented")
    }

    override fun getAppData(appDataId: Int): AppData {
        TODO("Not yet implemented")
    }

    override fun getAppDataList(): LiveData<List<AppData>> {
        TODO("Not yet implemented")
    }

}