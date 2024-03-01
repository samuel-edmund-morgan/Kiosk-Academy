package com.morgandev.kioskacademy.domain.repository

import androidx.lifecycle.LiveData
import com.morgandev.kioskacademy.domain.entity.Warrior
import java.util.UUID

interface WarriorsRepository {
    suspend fun addWarrior(warrior: Warrior)
    suspend fun deleteWarrior(warrior: Warrior)
    suspend fun editWarrior(warrior: Warrior)
    suspend fun getWarrior(warriorId: Int): Warrior
    fun getWarriorList(): LiveData<List<Warrior>>
}