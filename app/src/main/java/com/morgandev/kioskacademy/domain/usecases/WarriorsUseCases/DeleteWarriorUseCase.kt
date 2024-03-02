package com.morgandev.kioskacademy.domain.usecases.WarriorsUseCases

import com.morgandev.kioskacademy.domain.entities.Warrior
import com.morgandev.kioskacademy.domain.repositories.WarriorsRepository

class DeleteWarriorUseCase(private val warriorsRepository: WarriorsRepository) {
    suspend fun deleteWarrior(warrior: Warrior){
        warriorsRepository.deleteWarrior(warrior)
    }
}