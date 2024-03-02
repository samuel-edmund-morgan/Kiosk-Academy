package com.morgandev.kioskacademy.domain.usecases.AppDataUseCases

import com.morgandev.kioskacademy.domain.entity.AppData
import com.morgandev.kioskacademy.domain.repository.AppDataRepository

class AddAppDataUseCase(private val appDataRepository: AppDataRepository) {
        suspend fun addAppData(appData: AppData){
            appDataRepository.addAppData(appData)
        }
}