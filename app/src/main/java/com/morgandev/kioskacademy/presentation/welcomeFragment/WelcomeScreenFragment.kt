package com.morgandev.kioskacademy.presentation.welcomeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.morgandev.kioskacademy.R
import com.morgandev.kioskacademy.databinding.ActivityMainBinding
import com.morgandev.kioskacademy.databinding.FragmentWelcomeScreenBinding

class WelcomeScreenFragment : Fragment() {

    private var _binding: FragmentWelcomeScreenBinding? = null
    private val binding: FragmentWelcomeScreenBinding
        get() = _binding ?:  throw RuntimeException("FragmentWelcomeScreenBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentWelcomeScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Setup On Click Listeners
        setupOnClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupOnClickListeners(){
        with(binding) {
            yearImage.setOnClickListener {
                launchRecyclerViewWarriorsFragment()
            }
            mainMessage.setOnClickListener {
                launchRecyclerViewWarriorsFragment()
            }
            ssuEmblem.setOnClickListener {
                launchRecyclerViewWarriorsFragment()
            }
            bulletHole.setOnClickListener {
                launchRecyclerViewWarriorsFragment()
            }
            root.setOnClickListener {
                launchRecyclerViewWarriorsFragment()
            }
        }
    }

    private fun launchRecyclerViewWarriorsFragment(){
        findNavController().navigate(R.id.action_welcomeScreenFragment_to_recyclerViewWarriorsFragment)
    }

    companion object {
        fun newInstance() = WelcomeScreenFragment()
    }

}












//In onViewCreated
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


//In Activity
//Volume down button listener
//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
//            //val galleryNameUA = binding.dataToSave?.text.toString()
//            viewModel.addAppData(
//                AppData(1, byteArrayOf(0x48), 1,
//                    2014, "galleryNameUA", "Something")
//            )
//        }
//        return true
//    }