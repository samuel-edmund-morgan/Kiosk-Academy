package com.morgandev.kioskacademy.domain.usecases.AppDataUseCases

import com.morgandev.kioskacademy.domain.entities.AppData
import com.morgandev.kioskacademy.domain.repositories.AppDataRepository

class GetAppDataUseCase(private val appDataRepository: AppDataRepository) {
        suspend fun getAppData(appDataId: Int): AppData {
            return  appDataRepository.getAppData(appDataId)
        }
}