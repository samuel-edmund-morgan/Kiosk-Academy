package com.morgandev.kioskacademy.domain.repositories

import androidx.lifecycle.LiveData
import com.morgandev.kioskacademy.domain.entities.Warrior

interface WarriorsRepository {
    suspend fun addWarrior(warrior: Warrior)
    suspend fun deleteWarrior(warrior: Warrior)
    suspend fun editWarrior(warrior: Warrior)
    suspend fun getWarrior(warriorId: Int): Warrior
    fun getWarriorList(): LiveData<List<Warrior>>
}