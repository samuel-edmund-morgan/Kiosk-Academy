package com.morgandev.kioskacademy.domain.usecases.AppDataUseCases

import com.morgandev.kioskacademy.domain.entities.AppData
import com.morgandev.kioskacademy.domain.repositories.AppDataRepository

class AddAppDataUseCase(private val appDataRepository: AppDataRepository) {
        suspend fun addAppData(appData: AppData){
            appDataRepository.addAppData(appData)
        }
}