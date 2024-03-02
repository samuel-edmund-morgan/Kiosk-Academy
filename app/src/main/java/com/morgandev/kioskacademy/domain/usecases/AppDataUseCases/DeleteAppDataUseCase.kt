package com.morgandev.kioskacademy.domain.usecases.AppDataUseCases

import com.morgandev.kioskacademy.domain.entities.AppData
import com.morgandev.kioskacademy.domain.repositories.AppDataRepository

class DeleteAppDataUseCase(private val appDataRepository: AppDataRepository) {
        suspend fun deleteAppData(appData: AppData){
            appDataRepository.deleteAppData(appData)
        }
}