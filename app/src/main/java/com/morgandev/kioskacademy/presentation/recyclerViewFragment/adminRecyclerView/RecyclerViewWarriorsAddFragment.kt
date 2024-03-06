package com.morgandev.kioskacademy.presentation.recyclerViewFragment.adminRecyclerView

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.morgandev.kioskacademy.R
import com.morgandev.kioskacademy.databinding.FragmentRecyclerViewWarriorsAddBinding
import com.morgandev.kioskacademy.databinding.FragmentRecyclerViewWarriorsBinding
import com.morgandev.kioskacademy.domain.entities.Warrior
import com.morgandev.kioskacademy.presentation.recyclerViewFragment.RecyclerViewWarriorsViewModel
import com.morgandev.kioskacademy.presentation.welcomeFragment.BitmapConverter

class RecyclerViewWarriorsAddFragment : Fragment() {

    private val recycleViewWarriorsAddViewModel: RecyclerViewWarriorsViewModel by activityViewModels()

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var _binding: FragmentRecyclerViewWarriorsAddBinding? = null
    private val binding: FragmentRecyclerViewWarriorsAddBinding
        get() = _binding ?:  throw RuntimeException("FragmentRecyclerViewWarriorsAddBinding == null")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentRecyclerViewWarriorsAddBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        //testing
        val galleryImage = registerForActivityResult(ActivityResultContracts.GetContent(),
            ActivityResultCallback { it ->

                //saving image in room
                val newImage = requireActivity().contentResolver.openInputStream(it!!)?.readBytes()

                val newWarrior = Warrior(
                    newImage,
                    "Полковник",
                    "Олександр Аніщенко",
                    "Oleksandr Anishenko",
                    "Олександр Григорович Аніщенко",
                    "Oleksandr Hryhorovych Onishenko",
                    11,
                    11,
                    11,
                    "Descr",
                    listOf(byteArrayOf(1,2,3), byteArrayOf(1,2,3)),
                    listOf(byteArrayOf(1,2,3), byteArrayOf(1,2,3))
                )
                recycleViewWarriorsAddViewModel.addWarrior(newWarrior)
            }
        )
        binding.gallery.setOnClickListener {
            galleryImage.launch("image/*")
        }



        //Create function and use finishWork() onviewcreated use button back dispatcher + finishwork




//        launchAddMode()

    }

//    private fun launchAddMode() {
//        binding.testBtnAdd.setOnClickListener {
//            recycleViewWarriorsAddViewModel.a.addShopItem(
//                binding.etName.text?.toString(),
//                binding.etCount.text?.toString()
//            )
//        }
//    }

    private fun observeViewModel() {
            recycleViewWarriorsAddViewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
                onEditingFinishedListener.onEditingFinished()
            }

    }

    interface OnEditingFinishedListener {
        fun onEditingFinished()
    }

    companion object {
        fun newInstance() = RecyclerViewWarriorsAddFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


