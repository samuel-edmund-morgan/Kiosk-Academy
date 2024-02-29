package com.morgandev.kioskacademy.domain.usecases

import com.morgandev.kioskacademy.domain.entity.Warrior
import com.morgandev.kioskacademy.domain.repository.WarriorsRepository

class AddWarriorUseCase(private val warriorsRepository: WarriorsRepository) {
    fun addWarrior(warrior: Warrior){
        warriorsRepository.addWarrior(warrior)
    }
}