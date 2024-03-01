package com.morgandev.kioskacademy.domain.usecases

import com.morgandev.kioskacademy.domain.entity.Warrior
import com.morgandev.kioskacademy.domain.repository.WarriorsRepository
import java.util.UUID

class GetWarriorUseCase(private val warriorsRepository: WarriorsRepository) {
    suspend fun getWarrior(warriorId: Int): Warrior{
        return  warriorsRepository.getWarrior(warriorId)
    }
}