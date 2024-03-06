package com.morgandev.kioskacademy.presentation

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.morgandev.kioskacademy.R
import com.morgandev.kioskacademy.databinding.ActivityMainBinding
import com.morgandev.kioskacademy.presentation.recyclerViewFragment.RecyclerViewWarriorsViewModel
import com.morgandev.kioskacademy.presentation.recyclerViewFragment.adminRecyclerView.RecyclerViewWarriorsAddFragment


@Suppress("DEPRECATION")
//
class MainActivity : AppCompatActivity(), RecyclerViewWarriorsAddFragment.OnEditingFinishedListener
     {

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
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        return when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                if(findNavController(R.id.main_container).currentDestination?.id == R.id.recyclerViewWarriorsFragment) {
                    recycleViewWarriorsViewModel.setEvent(keyCode)
                }
                true
            }
            else -> super.onKeyDown(keyCode, event)
        }
    }



    override fun onEditingFinished() {
        supportFragmentManager.popBackStack()
    }


}


