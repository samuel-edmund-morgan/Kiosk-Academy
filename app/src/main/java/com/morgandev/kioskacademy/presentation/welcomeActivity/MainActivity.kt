package com.morgandev.kioskacademy.presentation.welcomeActivity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.morgandev.kioskacademy.databinding.ActivityMainBinding
import com.morgandev.kioskacademy.domain.entities.AppData
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Getting ViewModel
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        //Observing changes in LiveData
//        viewModel.appDataList.observe(this){
//            binding.showedData?.text = it.toString()
//        }



        //testing!
        //Trying to retrieve image from db
        //Working but not more that 3 Mb image!
        //So first screen will be just static!!!
//        viewModel.appDataList.observe(this){
//            if (it.isNotEmpty()) {
//                val bitmap = it.first().backgroundImage?.size?.let { it1 ->
//                    BitmapFactory.decodeByteArray(
//                        it.first().backgroundImage,0, it1
//                    )
//                }
//                binding.backgroundImage?.setImageBitmap(bitmap)
//            }
//        }






        //Open Gallery and chose one pic
        //Working but not more that 3 Mb image!
        //So first screen will be just static!!!
//        val galleryImage = registerForActivityResult(ActivityResultContracts.GetContent(),
//            ActivityResultCallback { it ->
//                binding.backgroundImage?.setImageURI(it)
//
//                //saving image in room
//                val newImage = contentResolver.openInputStream(it!!)?.readBytes()
//
//                val imageInString = BitmapConverter.converterBitmapToString(
//                    BitmapFactory.decodeByteArray(newImage, 0, newImage!!.size)
//                )
//                binding.showedData?.text = imageInString
//
//                val newAppData = AppData(
//                    1,
//                    newImage,
//                    1,
//                    2014,
//                    "",
//                    ""
//                )
//                viewModel.addAppData(newAppData)
//            }
//        )
//        binding.newBackground?.setOnClickListener {
//                galleryImage.launch("image/*")
//        }
    }

    //Volume down button listener
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            val galleryNameUA = binding.dataToSave?.text.toString()
            viewModel.addAppData(
                AppData(1, byteArrayOf(0x48), 1,
                    2014, galleryNameUA, "Something")
            )
        }
        return true
    }
}