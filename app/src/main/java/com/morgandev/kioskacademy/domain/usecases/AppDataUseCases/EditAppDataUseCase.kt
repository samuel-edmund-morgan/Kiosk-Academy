package com.morgandev.kioskacademy.domain.usecases.AppDataUseCases

import com.morgandev.kioskacademy.domain.entities.AppData
import com.morgandev.kioskacademy.domain.repositories.AppDataRepository

class EditAppDataUseCase(private val appDataRepository: AppDataRepository) {
        suspend fun editAppData(appData: AppData){
            appDataRepository.editAppData(appData)
        }
}