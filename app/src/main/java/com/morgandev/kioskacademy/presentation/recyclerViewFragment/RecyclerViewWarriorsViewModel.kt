package com.morgandev.kioskacademy.presentation.recyclerViewFragment

import android.app.Application
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.morgandev.kioskacademy.R
import com.morgandev.kioskacademy.data.WarriorsData.WarriorListRepositoryImpl
import com.morgandev.kioskacademy.domain.entities.Warrior
import com.morgandev.kioskacademy.domain.usecases.WarriorsUseCases.AddWarriorUseCase
import com.morgandev.kioskacademy.domain.usecases.WarriorsUseCases.DeleteWarriorUseCase
import com.morgandev.kioskacademy.domain.usecases.WarriorsUseCases.EditWarriorUseCase
import com.morgandev.kioskacademy.domain.usecases.WarriorsUseCases.GetWarriorListUseCase
import com.morgandev.kioskacademy.presentation.Event
import kotlinx.coroutines.launch

class RecyclerViewWarriorsViewModel(application: Application) : AndroidViewModel(application)  {

    private val _keyEvent = MutableLiveData<Event<Int>>()
    val keyEvent: LiveData<Event<Int>>
        get() = _keyEvent

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    private val repository = WarriorListRepositoryImpl(application)

    private val getWarriorListUseCase = GetWarriorListUseCase(repository)
    private val deleteWarriorUseCase = DeleteWarriorUseCase(repository)
    private val addWarriorUseCase = AddWarriorUseCase(repository)

    private val editWarriorUseCase = EditWarriorUseCase(repository)

    val warriorList = getWarriorListUseCase.getWarriorList()

    fun setEvent(code:Int){
        _keyEvent.value = Event(code)
    }

    fun deleteWarrior(warrior: Warrior) {
        viewModelScope.launch {
            deleteWarriorUseCase.deleteWarrior(warrior)
        }
    }

    fun addWarrior(warrior: Warrior){
        viewModelScope.launch {
            addWarriorUseCase.addWarrior(warrior)
            finishWork()
        }
    }


//    fun addShopItem(inputName: String?, inputCount: String?) {
//        val name = parseName(inputName)
//        val count = parseCount(inputCount)
//        val fieldsValid = validateInput(name, count)
//        if (fieldsValid) {
//            viewModelScope.launch {
//                val shopItem = ShopItem(name, count, true)
//                addShopItemUseCase.addShopItem(shopItem)
//                finishWork()
//            }
//        }
//    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }




}