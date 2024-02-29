package com.morgandev.kioskacademy.domain.repository

import androidx.lifecycle.LiveData
import com.morgandev.kioskacademy.domain.entity.Warrior
import java.util.UUID

interface WarriorsRepository {
    fun addWarrior(warrior: Warrior)
    fun deleteWarrior(warrior: Warrior)
    fun editWarrior(warrior: Warrior)
    fun getWarrior(warriorId: UUID): Warrior
    fun getWarriorList(): LiveData<List<Warrior>>
}