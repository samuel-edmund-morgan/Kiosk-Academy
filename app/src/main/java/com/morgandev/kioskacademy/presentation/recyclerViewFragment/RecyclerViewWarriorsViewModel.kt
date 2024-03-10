package com.morgandev.kioskacademy.presentation.recyclerViewFragment

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.morgandev.kioskacademy.R
import com.morgandev.kioskacademy.data.WarriorsData.WarriorListRepositoryImpl
import com.morgandev.kioskacademy.databinding.FragmentRecyclerViewWarriorsAddBinding
import com.morgandev.kioskacademy.domain.entities.Warrior
import com.morgandev.kioskacademy.domain.usecases.WarriorsUseCases.AddWarriorUseCase
import com.morgandev.kioskacademy.domain.usecases.WarriorsUseCases.DeleteWarriorUseCase
import com.morgandev.kioskacademy.domain.usecases.WarriorsUseCases.EditWarriorUseCase
import com.morgandev.kioskacademy.domain.usecases.WarriorsUseCases.GetWarriorListUseCase
import com.morgandev.kioskacademy.presentation.Event
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.File

class RecyclerViewWarriorsViewModel(application: Application) : AndroidViewModel(application)  {

    private val context: Context
        get() = getApplication<Application>().applicationContext

    private val _keyEvent = MutableLiveData<Event<Int>>()
    val keyEvent: LiveData<Event<Int>>
        get() = _keyEvent

    private val _shouldCloseScreen = MutableLiveData<Event<Unit>>()
    val shouldCloseScreen: LiveData<Event<Unit>>
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

    fun copyFromCacheToFiles(binding: FragmentRecyclerViewWarriorsAddBinding){
        viewModelScope.launch {
            val profilePhoto = binding.profileTv.text.toString().trim()
            val emblem = binding.emblemTv.text.toString().trim()
            val rank = binding.rankIdEt.text.toString().trim()
            val nameSurname = binding.nameSurnameEt.text.toString().trim()
            val surnameNamePatronim = binding.surnameNamePatronimEt.text.toString().trim()
            val dateBirth = binding.birthTv.text.toString().trim()
            val dateDeath = binding.deathTv.text.toString().trim()
            val description = binding.descriptionEt.text.toString().trim()
            val photoGallery = binding.photoTv.text.toString().trim()
            val videoGallery = binding.videoTv.text.toString().trim()

            //Create directory named as profile picture
            val filesDir = context.filesDir
            val warriorFolderPath = File("$filesDir/$profilePhoto")
            warriorFolderPath.mkdirs()


            //Profile photo from tmp to persistent storage
            val fullTmpPathProfilePhoto = File(context.cacheDir, profilePhoto)
            val fullFilePathProfilePhoto = File(warriorFolderPath, profilePhoto)
            val profilePhotoBytes = fullTmpPathProfilePhoto.readBytes()
            val inputStreamPhoto = ByteArrayInputStream(profilePhotoBytes)
            inputStreamPhoto.use { input ->
                fullFilePathProfilePhoto.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            fullTmpPathProfilePhoto.delete()

            //Emblem if exist from tmp to persistent storage
            if (emblem != ""){
                val fullTmpPathEmblem = File(context.cacheDir, emblem)
                val fullFilePathEmblem = File(warriorFolderPath, emblem)
                val emblemBytes = fullTmpPathEmblem.readBytes()
                val inputStreamEmblem = ByteArrayInputStream(emblemBytes)
                inputStreamEmblem.use { input ->
                    fullFilePathEmblem.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
                fullTmpPathEmblem.delete()
            }

            //Gallery photos from gallery tmp to persistent storage
            if (photoGallery != ""){
                val namesOfPhotos = toListStringFromString(photoGallery)
                for (photo in namesOfPhotos){
                    val fullTmpPathPhoto = File(context.cacheDir, photo)
                    val fullFilePathPhoto = File(warriorFolderPath, photo)
                    val photoBytes = fullTmpPathPhoto.readBytes()
                    val inputStreamPhotoG = ByteArrayInputStream(photoBytes)
                    inputStreamPhotoG.use { input ->
                        fullFilePathPhoto.outputStream().use { output ->
                            input.copyTo(output)
                        }
                    }
                    fullTmpPathPhoto.delete()
                }
            }

            //Gallery videos from gallery tmp to persistent storage
            if (videoGallery != ""){
                val namesOfVideos = toListStringFromString(videoGallery)
                for (video in namesOfVideos){
                    val fullTmpPathVideo = File(context.cacheDir, video)
                    val fullFilePathVideo = File(warriorFolderPath, video)
                    val videoBytes = fullTmpPathVideo.readBytes()
                    val inputStreamVideoG = ByteArrayInputStream(videoBytes)
                    inputStreamVideoG.use { input ->
                        fullFilePathVideo.outputStream().use { output ->
                            input.copyTo(output)
                        }
                    }
                    fullTmpPathVideo.delete()
                }
            }
            addWarrior(Warrior(
                profilePhoto,
                rank,
                nameSurname,
                surnameNamePatronim,
                emblem,
                dateBirth,
                dateDeath,
                description,
                photoGallery,
                videoGallery
            ))
        }
    }

    private fun toListStringFromString(stringList: String): List<String> {
        val result = ArrayList<String>()
        val split = stringList.replace("[","").replace("]","")
            .replace(" ","").split(",")
        for (n in split) {
            try {
                result.add(n)
            } catch (e: Exception) {

            }
        }
        return result
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
        _shouldCloseScreen.value = Event<Unit>(Unit)
    }




}