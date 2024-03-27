package com.morgandev.kioskacademy.presentation.recyclerViewFragment

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.FileUtils.openInputStream
import com.morgandev.kioskacademy.data.VideosData.VideosListRepositoryImpl
import com.morgandev.kioskacademy.data.WarriorsData.WarriorListRepositoryImpl
import com.morgandev.kioskacademy.databinding.FragmentRecyclerViewWarriorsAddBinding
import com.morgandev.kioskacademy.domain.entities.VideoData
import com.morgandev.kioskacademy.domain.entities.Warrior
import com.morgandev.kioskacademy.domain.usecases.VideosUseCase.AddVideoUseCase
import com.morgandev.kioskacademy.domain.usecases.VideosUseCase.DeleteVideoUseCase
import com.morgandev.kioskacademy.domain.usecases.VideosUseCase.GetVideosListUseCase
import com.morgandev.kioskacademy.domain.usecases.WarriorsUseCases.AddWarriorUseCase
import com.morgandev.kioskacademy.domain.usecases.WarriorsUseCases.DeleteWarriorUseCase
import com.morgandev.kioskacademy.domain.usecases.WarriorsUseCases.EditWarriorUseCase
import com.morgandev.kioskacademy.domain.usecases.WarriorsUseCases.GetWarriorListUseCase
import com.morgandev.kioskacademy.presentation.Event
import kotlinx.coroutines.launch
import java.io.BufferedOutputStream
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


    private val repository = VideosListRepositoryImpl(application)

    private val addVideoUseCase = AddVideoUseCase(repository)
    private val getVideosListUseCase = GetVideosListUseCase(repository)
    private val deleteVideoUseCase = DeleteVideoUseCase(repository)

    val videoList = getVideosListUseCase.getVideosList()

    fun setEvent(code:Int){
        _keyEvent.value = Event(code)
    }

    fun deleteVideo(videoData: VideoData) {
        viewModelScope.launch {
            deleteVideoUseCase.deleteVideo(videoData)
        }
    }

    fun addVideoData(videoData: VideoData){
        viewModelScope.launch {
            addVideoUseCase.addVideoData(videoData)
            finishWork()
        }
    }

    fun copyFromCacheToFiles(binding: FragmentRecyclerViewWarriorsAddBinding){
        viewModelScope.launch {

            val videoName = binding.videoNameEt.text.toString().trim()
            val videoFileName = binding.videoFileNameTv.text.toString().trim()


            //Create directory named as video file
            val filesDir = context.filesDir
            val videoFolderPath = File("$filesDir/$videoFileName")
            videoFolderPath.mkdirs()

            moveToPersistentStorage(
                File(context.cacheDir, videoFileName),
                File(videoFolderPath, videoFileName)
            )

            addVideoData(
                VideoData(
                videoName,
                videoFileName
            )
            )
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