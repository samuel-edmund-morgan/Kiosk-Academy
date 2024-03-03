package com.morgandev.kioskacademy.presentation.welcomeFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.morgandev.kioskacademy.data.AppData.AppDataListRepositoryImpl
import com.morgandev.kioskacademy.domain.entities.AppData
import com.morgandev.kioskacademy.domain.usecases.AppDataUseCases.AddAppDataUseCase
import com.morgandev.kioskacademy.domain.usecases.AppDataUseCases.DeleteAppDataUseCase
import com.morgandev.kioskacademy.domain.usecases.AppDataUseCases.EditAppDataUseCase
import com.morgandev.kioskacademy.domain.usecases.AppDataUseCases.GetAppDataListUseCase
import com.morgandev.kioskacademy.domain.usecases.AppDataUseCases.GetAppDataUseCase
import kotlinx.coroutines.launch

class WelcomeFragmentViewModel(application: Application) : AndroidViewModel(application)  {

    private val repository = AppDataListRepositoryImpl(application)

    private val addAppDataUseCase = AddAppDataUseCase(repository)
    private val deleteAppDataUseCase = DeleteAppDataUseCase(repository)
    private val editAppDataUseCase = EditAppDataUseCase(repository)
    private val getAppDataListUseCase = GetAppDataListUseCase(repository)
    private val getAppDataUseCase = GetAppDataUseCase(repository)

    fun addAppData(appData: AppData){
        viewModelScope.launch {
            addAppDataUseCase.addAppData(appData)
        }
    }
    fun deleteAppData(appData: AppData) {
        viewModelScope.launch {
            deleteAppDataUseCase.deleteAppData(appData)
        }
    }
    fun editAppData(appData: AppData) {
        viewModelScope.launch {
            editAppDataUseCase.editAppData(appData)
        }
    }

    val appDataList = getAppDataListUseCase.getAppDataList()

    fun getAppData(appDataId: Int){
        viewModelScope.launch {
            getAppDataUseCase.getAppData(appDataId)
        }
    }







}