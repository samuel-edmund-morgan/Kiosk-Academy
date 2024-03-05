package com.morgandev.kioskacademy.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.morgandev.kioskacademy.databinding.ActivityMainBinding
import com.morgandev.kioskacademy.presentation.recyclerViewFragment.RecyclerViewWarriorsViewModel
import com.morgandev.kioskacademy.presentation.welcomeFragment.WelcomeFragmentViewModel


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {


    private val recycleViewWarriorsViewModel: RecyclerViewWarriorsViewModel by viewModels()


    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw RuntimeException("ActivityMainBinding == null")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideNavBar()
    }

    private fun hideNavBar(){
        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //In Activity
//Volume down button listener
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            Toast.makeText(this, "TEST", Toast.LENGTH_LONG).show()
            recycleViewWarriorsViewModel.setEvent(keyCode)
            //val galleryNameUA = binding.dataToSave?.text.toString()
//            viewModel.addAppData(
//                AppData(1, byteArrayOf(0x48), 1,
//                    2014, "galleryNameUA", "Something")
//            )
        }
        return true
    }



}