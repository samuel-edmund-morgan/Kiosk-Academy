package com.morgandev.kioskacademy.domain.usecases.WarriorsUseCases

import com.morgandev.kioskacademy.domain.entities.Warrior
import com.morgandev.kioskacademy.domain.repositories.WarriorsRepository

class EditWarriorUseCase(private val warriorsRepository: WarriorsRepository) {
    suspend fun editWarrior(warrior: Warrior){
        warriorsRepository.editWarrior(warrior)
    }
}