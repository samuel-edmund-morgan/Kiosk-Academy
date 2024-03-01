package com.morgandev.kioskacademy.data

import androidx.lifecycle.LiveData
import com.morgandev.kioskacademy.domain.entity.Warrior
import com.morgandev.kioskacademy.domain.repository.WarriorsRepository


class AppDataListRepositoryImpl(): WarriorsRepository {
    override suspend fun addWarrior(warrior: Warrior) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteWarrior(warrior: Warrior) {
        TODO("Not yet implemented")
    }

    override suspend fun editWarrior(warrior: Warrior) {
        TODO("Not yet implemented")
    }

    override suspend fun getWarrior(warriorId: Int): Warrior {
        TODO("Not yet implemented")
    }

    override fun getWarriorList(): LiveData<List<Warrior>> {
        TODO("Not yet implemented")
    }
}