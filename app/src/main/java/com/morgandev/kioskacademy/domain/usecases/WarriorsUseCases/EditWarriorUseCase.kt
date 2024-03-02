package com.morgandev.kioskacademy.domain.usecases.WarriorsUseCases

import com.morgandev.kioskacademy.domain.entity.Warrior
import com.morgandev.kioskacademy.domain.repository.WarriorsRepository

class EditWarriorUseCase(private val warriorsRepository: WarriorsRepository) {
    suspend fun editWarrior(warrior: Warrior){
        warriorsRepository.editWarrior(warrior)
    }
}