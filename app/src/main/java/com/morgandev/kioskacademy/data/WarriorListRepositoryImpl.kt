package com.morgandev.kioskacademy.data

import androidx.lifecycle.LiveData
import com.morgandev.kioskacademy.domain.entity.Warrior
import com.morgandev.kioskacademy.domain.repository.WarriorsRepository
import java.util.UUID
import javax.inject.Inject

class WarriorListRepositoryImpl @Inject constructor(
    private val warriorListDao: WarriorListDao,
    private val mapper: WarriorListMapper
): WarriorsRepository {
    override fun addWarrior(warrior: Warrior) {
        TODO("Not yet implemented")
    }

    override fun deleteWarrior(warrior: Warrior) {
        TODO("Not yet implemented")
    }

    override fun editWarrior(warrior: Warrior) {
        TODO("Not yet implemented")
    }

    override fun getWarrior(warriorId: Int): Warrior {
        TODO("Not yet implemented")
    }

    override fun getWarriorList(): LiveData<List<Warrior>> {
        TODO("Not yet implemented")
    }
}