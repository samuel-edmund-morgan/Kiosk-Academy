package com.morgandev.kioskacademy.presentation.recyclerViewFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.morgandev.kioskacademy.data.WarriorsData.WarriorListRepositoryImpl
import com.morgandev.kioskacademy.domain.entities.Warrior
import com.morgandev.kioskacademy.domain.usecases.WarriorsUseCases.AddWarriorUseCase
import com.morgandev.kioskacademy.domain.usecases.WarriorsUseCases.DeleteWarriorUseCase
import com.morgandev.kioskacademy.domain.usecases.WarriorsUseCases.EditWarriorUseCase
import com.morgandev.kioskacademy.domain.usecases.WarriorsUseCases.GetWarriorListUseCase
import kotlinx.coroutines.launch

class RecyclerWarriorsViewModel(application: Application) : AndroidViewModel(application)  {

    private val repository = WarriorListRepositoryImpl(application)

    private val getWarriorListUseCase = GetWarriorListUseCase(repository)
    private val deleteWarriorUseCase = DeleteWarriorUseCase(repository)
    private val addWarriorUseCase = AddWarriorUseCase(repository)

    private val editWarriorUseCase = EditWarriorUseCase(repository)

    val warriorList = getWarriorListUseCase.getWarriorList()

    fun deleteWarrior(warrior: Warrior) {
        viewModelScope.launch {
            deleteWarriorUseCase.deleteWarrior(warrior)
        }
    }

    fun addWarrior(warrior: Warrior){
        viewModelScope.launch {
            addWarriorUseCase.addWarrior(warrior)
        }
    }




}