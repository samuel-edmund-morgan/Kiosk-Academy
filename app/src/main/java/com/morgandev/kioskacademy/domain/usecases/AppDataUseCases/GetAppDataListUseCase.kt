package com.morgandev.kioskacademy.domain.usecases.AppDataUseCases

import androidx.lifecycle.LiveData
import com.morgandev.kioskacademy.domain.entities.AppData
import com.morgandev.kioskacademy.domain.repositories.AppDataRepository

class GetAppDataListUseCase(private val appDataRepository: AppDataRepository) {
        fun getAppDataList(): LiveData<List<AppData>> {
            return appDataRepository.getAppDataList()
        }
}