package com.morgandev.kioskacademy.domain.usecases.AppDataUseCases

import com.morgandev.kioskacademy.domain.entity.AppData
import com.morgandev.kioskacademy.domain.repository.AppDataRepository

class DeleteAppDataUseCase(private val appDataRepository: AppDataRepository) {
        suspend fun deleteAppData(appData: AppData){
            appDataRepository.deleteAppData(appData)
        }
}