package com.morgandev.kioskacademy.domain.usecases.WarriorsUseCases

import androidx.lifecycle.LiveData
import com.morgandev.kioskacademy.domain.entity.Warrior
import com.morgandev.kioskacademy.domain.repository.WarriorsRepository

class GetWarriorListUseCase(private val warriorsRepository: WarriorsRepository) {
    fun getWarriorList(): LiveData<List<Warrior>>{
        return warriorsRepository.getWarriorList()
    }
}