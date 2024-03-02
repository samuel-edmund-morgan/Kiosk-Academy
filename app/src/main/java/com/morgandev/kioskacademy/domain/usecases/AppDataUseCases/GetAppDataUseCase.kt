package com.morgandev.kioskacademy.domain.usecases.AppDataUseCases

import com.morgandev.kioskacademy.domain.entity.AppData
import com.morgandev.kioskacademy.domain.repository.AppDataRepository

class GetAppDataUseCase(private val appDataRepository: AppDataRepository) {
        suspend fun getAppData(appDataId: Int): AppData {
            return  appDataRepository.getAppData(appDataId)
        }
}