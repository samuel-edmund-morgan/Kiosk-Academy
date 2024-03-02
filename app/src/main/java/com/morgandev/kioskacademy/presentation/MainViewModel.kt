package com.morgandev.kioskacademy.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.morgandev.kioskacademy.data.AppData.AppDataListRepositoryImpl
import com.morgandev.kioskacademy.domain.entity.Warrior
import com.morgandev.kioskacademy.domain.usecases.WarriorsUseCases.AddWarriorUseCase
import com.morgandev.kioskacademy.domain.usecases.WarriorsUseCases.DeleteWarriorUseCase
import com.morgandev.kioskacademy.domain.usecases.WarriorsUseCases.EditWarriorUseCase
import com.morgandev.kioskacademy.domain.usecases.WarriorsUseCases.GetWarriorListUseCase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application)  {

    private val repository = AppDataListRepositoryImpl(application)

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