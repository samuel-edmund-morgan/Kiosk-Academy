package com.morgandev.kioskacademy.domain.usecases.WarriorsUseCases

import androidx.lifecycle.LiveData
import com.morgandev.kioskacademy.domain.entities.Warrior
import com.morgandev.kioskacademy.domain.repositories.WarriorsRepository

class GetWarriorListUseCase(private val warriorsRepository: WarriorsRepository) {
    fun getWarriorList(): LiveData<List<Warrior>>{
        return warriorsRepository.getWarriorList()
    }
}