package com.morgandev.kioskacademy.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WarriorListDao {
    @Query("SELECT * FROM warriors")
    fun getWarriorsList(): LiveData<List<WarriorDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWarrior(warriorDbModel: WarriorDbModel)

    @Query("DELETE FROM warriors WHERE id=:warriorId")
    suspend fun deleteWarrior(warriorId: Int)

    @Query("SELECT * FROM warriors WHERE id=:warriorId LIMIT 1")
    suspend fun getWarrior(warriorId: Int): WarriorDbModel


}