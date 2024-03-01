package com.morgandev.kioskacademy.domain.usecases

import com.morgandev.kioskacademy.domain.entity.Warrior
import com.morgandev.kioskacademy.domain.repository.WarriorsRepository

class DeleteWarriorUseCase(private val warriorsRepository: WarriorsRepository) {
    suspend fun deleteWarrior(warrior: Warrior){
        warriorsRepository.deleteWarrior(warrior)
    }
}