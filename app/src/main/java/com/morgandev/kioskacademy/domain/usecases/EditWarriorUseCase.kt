package com.morgandev.kioskacademy.domain.usecases

import com.morgandev.kioskacademy.domain.entity.Warrior
import com.morgandev.kioskacademy.domain.repository.WarriorsRepository

class EditWarriorUseCase(private val warriorsRepository: WarriorsRepository) {
    fun editWarrior(warrior: Warrior){
        warriorsRepository.editWarrior(warrior)
    }
}