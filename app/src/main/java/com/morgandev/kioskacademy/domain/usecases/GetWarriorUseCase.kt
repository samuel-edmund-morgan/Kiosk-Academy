package com.morgandev.kioskacademy.domain.usecases

import com.morgandev.kioskacademy.domain.entity.Warrior
import com.morgandev.kioskacademy.domain.repository.WarriorsRepository
import java.util.UUID

class GetWarriorUseCase(private val warriorsRepository: WarriorsRepository) {
    fun getWarrior(warriorId: UUID): Warrior{
        return  warriorsRepository.getWarrior(warriorId)
    }
}