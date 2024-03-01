package com.morgandev.kioskacademy.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.morgandev.kioskacademy.domain.entity.AppData
import com.morgandev.kioskacademy.domain.entity.Warrior
import com.morgandev.kioskacademy.domain.repository.AppDataRepository
import com.morgandev.kioskacademy.domain.repository.WarriorsRepository


class AppDataListRepositoryImpl(
    application: Application
): AppDataRepository {

    private val appDataDao = ApplicationDatabase.getInstance(application).appDataDao()
    private val mapper = AppDataMapper()

    override suspend fun addAppData(appData: AppData) {
        appDataDao.addAppData(mapper.mapEntityToDbModel(appData))
    }

    override suspend fun deleteAppData(appData: AppData) {
        appDataDao.deleteAppData(appData.id)
    }

    override suspend fun editAppData(appData: AppData) {
        appDataDao.addAppData(mapper.mapEntityToDbModel(appData))
    }

    override suspend fun getAppData(appDataId: Int): AppData {
        val dbModel = appDataDao.getAppData(appDataId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getAppDataList(): LiveData<List<AppData>> = appDataDao.getAppDataList().map {
        mapper.mapListDbModelToListEntity(it)
    }

}