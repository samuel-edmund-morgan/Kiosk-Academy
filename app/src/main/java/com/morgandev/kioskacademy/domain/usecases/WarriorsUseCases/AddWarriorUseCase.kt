package com.morgandev.kioskacademy.domain.usecases.WarriorsUseCases

import com.morgandev.kioskacademy.domain.entities.Warrior
import com.morgandev.kioskacademy.domain.repositories.WarriorsRepository

class AddWarriorUseCase(private val warriorsRepository: WarriorsRepository) {
    suspend fun addWarrior(warrior: Warrior){
        warriorsRepository.addWarrior(warrior)
    }
}