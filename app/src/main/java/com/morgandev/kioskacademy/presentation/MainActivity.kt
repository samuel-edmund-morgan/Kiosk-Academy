package com.morgandev.kioskacademy.presentation

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.morgandev.kioskacademy.R
import com.morgandev.kioskacademy.databinding.ActivityMainBinding
import com.morgandev.kioskacademy.domain.entity.Warrior

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.warriorList.observe(this){
            binding.showedData?.text = it.toString()
        }



        //testing
        val galleryImage = registerForActivityResult(ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                binding.backgroundImage?.setImageURI(it)
            }
        )




        binding.saveBtn?.setOnClickListener {
                val rank = binding.dataToSave?.text.toString()
                //binding.root.setBackgroundResource(R.drawable.backgr)
                galleryImage.launch("image/*")
                viewModel.addWarrior(
                    Warrior(1,1,rank, "Egor",
                        "Egor", "Egor Parh", "Egor Parkh",
                        12, 1998, 2023, "Some descr",
                        listOf(3,4,5), listOf(3,24,43,))
                )
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            val rank = binding.dataToSave?.text.toString()
            viewModel.addWarrior(
                Warrior(1,1,rank, "Egor",
                    "Egor", "Egor Parh", "Egor Parkh",
                    12, 1998, 2023, "Some descr",
                    listOf(3,4,5), listOf(3,24,43,))
            )
        }
        return true
    }
}