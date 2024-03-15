package com.morgandev.kioskacademy.presentation.recyclerViewFragment

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.FileUtils.openInputStream
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
import java.io.BufferedOutputStream
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

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
            val profileDetailedPhoto = binding.profilePhotoDetailedTv.text.toString().trim()
            val emblem = binding.emblemTv.text.toString().trim()
            val rank = binding.rankIdEt.text.toString().trim()
            val nameSurname = binding.nameSurnameEt.text.toString().trim()
            val surnameNamePatronim = binding.surnameNamePatronimEt.text.toString().trim()
            val dateBirth = binding.birthTv.text.toString().trim()
            val dateDeath = binding.deathTv.text.toString().trim()
            val description = binding.descriptionTv.text.toString().trim()
            val photoGallery = binding.photoTv.text.toString().trim()
            val videoGallery = binding.videoTv.text.toString().trim()

            //Create directory named as profile picture
            val filesDir = context.filesDir
            val warriorFolderPath = File("$filesDir/$profilePhoto")
            warriorFolderPath.mkdirs()

            moveToPersistentStorage(
                File(context.cacheDir, profilePhoto),
                File(warriorFolderPath, profilePhoto)
            )
            moveToPersistentStorage(
                File(context.cacheDir, profileDetailedPhoto),
                File(warriorFolderPath, profileDetailedPhoto)
            )
            moveToPersistentStorage(
                File(context.cacheDir, description),
                File(warriorFolderPath, description)
            )
            if(emblem != ""){
                moveToPersistentStorage(
                    File(context.cacheDir, emblem),
                    File(warriorFolderPath, emblem)
                )
            }
            if (photoGallery != ""){
                val namesOfPhotos = toListStringFromString(photoGallery)
                for (photo in namesOfPhotos){
                    moveToPersistentStorage(
                        File(context.cacheDir, photo),
                        File(warriorFolderPath, photo)
                    )
                }
            }
            if (videoGallery != ""){
                val namesOfVideos = toListStringFromString(videoGallery)
                for (video in namesOfVideos){
                    moveToPersistentStorage(
                        File(context.cacheDir, video),
                        File(warriorFolderPath, video)
                    )
                }
            }
            addWarrior(Warrior(
                profilePhoto,
                profileDetailedPhoto,
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

    @Throws(IOException::class)
    private fun writeToFile(inputStream: InputStream?, path: String) {
        val output = BufferedOutputStream(FileOutputStream(path))
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len: Int
        while (inputStream?.read(buffer).also { len = it!! } != -1) {
            output.write(buffer, 0, len)
        }
        output.close()
    }

    private fun moveToPersistentStorage(from: File, to: File){
        openInputStream(from).use {
            writeToFile(it, to.absolutePath)
        }
        from.delete()
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Event<Unit>(Unit)
    }




}