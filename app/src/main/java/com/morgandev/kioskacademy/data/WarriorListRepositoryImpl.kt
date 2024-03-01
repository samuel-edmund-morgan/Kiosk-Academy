package com.morgandev.kioskacademy.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.morgandev.kioskacademy.domain.entity.Warrior
import com.morgandev.kioskacademy.domain.repository.WarriorsRepository
import androidx.lifecycle.map

class WarriorListRepositoryImpl(
    application: Application
): WarriorsRepository {

    private val warriorListDao = ApplicationDatabase.getInstance(application).warriorListDao()
    private val mapper = WarriorListMapper()
    override suspend fun addWarrior(warrior: Warrior) {
        warriorListDao.addWarrior(mapper.mapEntityToDbModel(warrior))
    }

    override suspend fun deleteWarrior(warrior: Warrior) {
        warriorListDao.deleteWarrior(warrior.id)
    }

    override suspend fun editWarrior(warrior: Warrior) {
        warriorListDao.addWarrior(mapper.mapEntityToDbModel(warrior))
    }

    override suspend fun getWarrior(warriorId: Int): Warrior {
        val dbModel = warriorListDao.getWarrior(warriorId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getWarriorList(): LiveData<List<Warrior>> = warriorListDao.getWarriorsList().map {
        mapper.mapListDbModelToListEntity(it)
    }


}