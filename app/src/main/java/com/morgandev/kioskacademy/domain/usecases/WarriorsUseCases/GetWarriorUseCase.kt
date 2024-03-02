package com.morgandev.kioskacademy.domain.usecases.WarriorsUseCases

import com.morgandev.kioskacademy.domain.entities.Warrior
import com.morgandev.kioskacademy.domain.repositories.WarriorsRepository

class GetWarriorUseCase(private val warriorsRepository: WarriorsRepository) {
    suspend fun getWarrior(warriorId: Int): Warrior{
        return  warriorsRepository.getWarrior(warriorId)
    }
}