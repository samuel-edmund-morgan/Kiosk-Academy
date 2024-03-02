package com.morgandev.kioskacademy.domain.usecases.AppDataUseCases

import com.morgandev.kioskacademy.domain.entity.AppData
import com.morgandev.kioskacademy.domain.repository.AppDataRepository

class EditAppDataUseCase(private val appDataRepository: AppDataRepository) {
        suspend fun editAppData(appData: AppData){
            appDataRepository.editAppData(appData)
        }
}