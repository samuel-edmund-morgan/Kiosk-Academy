package com.morgandev.kioskacademy.presentation.recyclerViewFragment.adminRecyclerView

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.morgandev.kioskacademy.databinding.FragmentRecyclerViewWarriorsAddBinding
import com.morgandev.kioskacademy.presentation.recyclerViewFragment.RecyclerViewWarriorsViewModel
import java.io.ByteArrayInputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
        _binding =  FragmentRecyclerViewWarriorsAddBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()

        with(binding){

            choseFileClickListener(profileTv,
                profileBtn,
                ActivityResultContracts.PickVisualMedia.ImageOnly,
                1)

            choseFileClickListener(emblemTv,
                emblemBtn,
                ActivityResultContracts.PickVisualMedia.ImageOnly,
                1)

            choseDateClickListener(birthTv, birthBtn)
            choseDateClickListener(deathTv, deathBtn)

            choseFileClickListener(photoTv,
                photoBtn,
                ActivityResultContracts.PickVisualMedia.ImageOnly,
                15)

            choseFileClickListener(videoTv,
                videoBtn,
                ActivityResultContracts.PickVisualMedia.VideoOnly,
                5)
        }

    }


    private fun observeViewModel() {
            recycleViewWarriorsAddViewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
                onEditingFinishedListener.onEditingFinished()
            }
    }

    private fun choseDateClickListener(textView: TextView, button: Button) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = context?.let {
            DatePickerDialog(
                it, { DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, monthOfYear, dayOfMonth)
                    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("uk","UA"))
                    val formattedDate = dateFormat.format(selectedDate.time)
                    textView.text = formattedDate
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        }
        button.setOnClickListener {
            datePickerDialog?.show()
        }
    }


    private fun choseFileClickListener(textView: TextView,
                                       button: Button,
                                       visualMediaType: ActivityResultContracts.PickVisualMedia.VisualMediaType,
                                       fileCount: Int){
        // Registers a photo picker activity launcher in single-select mode.
        val contract = if (fileCount == 1) ActivityResultContracts.PickVisualMedia() else
           ActivityResultContracts.PickMultipleVisualMedia(fileCount)
        val pickMedia = registerForActivityResult(contract) { uri ->


            if (uri != null) {
                if (uri is List<*>){
                    for(uri_item in uri){
                        if(uri_item is Uri){
                            val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                            context?.contentResolver?.takePersistableUriPermission(uri_item, flag)

                            //Create file in tmp dir and write video there:
                            val fileName = File(uri_item.path).name
                            val fullFilePath =  File(context?.cacheDir, fileName)
                            val newVideo = requireActivity().contentResolver.openInputStream(uri_item)?.readBytes()
                            val inputStream = ByteArrayInputStream(newVideo)
                            inputStream.use { input ->
                                fullFilePath.outputStream().use { output ->
                                    input.copyTo(output)
                                }
                            }

                            //Also must write name of the file (fileName) to TextView on Fragment
                            textView.text = fileName

                            //After all input fields are filled you must copy file (fileName) from cache to files
                            //and write NAME of the file(fileName, not path) to Room db and clear cache
                        }
                    }
                }
                else if (uri is Uri){
                    val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    context?.contentResolver?.takePersistableUriPermission(uri, flag)

                    //Create file in tmp dir and write video there:
                    val fileName = File(uri.path).name
                    val fullFilePath =  File(context?.cacheDir, fileName)
                    val newVideo = requireActivity().contentResolver.openInputStream(uri)?.readBytes()
                    val inputStream = ByteArrayInputStream(newVideo)
                    inputStream.use { input ->
                        fullFilePath.outputStream().use { output ->
                            input.copyTo(output)
                        }
                    }

                    //Also must write name of the file (fileName) to TextView on Fragment
                    textView.text = fileName

                    //After all input fields are filled you must copy file (fileName) from cache to files
                    //and write NAME of the file(fileName, not path) to Room db and clear cache
                }

            } else {
                Toast.makeText(context, "No media selected", Toast.LENGTH_LONG).show()
                Log.d("PhotoPicker", "No media selected")
            }
        }
        button.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(visualMediaType))
        }

    }

    interface OnEditingFinishedListener {
        fun onEditingFinished()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




//testing
//                val galleryImage = registerForActivityResult(ActivityResultContracts.GetContent(),
//            ActivityResultCallback { it ->
//
//                //saving image in room
//                val                 val galleryImage = registerForActivityResult(ActivityResultContracts.GetContent(),
////            ActivityResultCallback { it ->
////
////                //saving image in room
////                val newImage = requireActivity().contentResolver.openInputStream(it!!)?.readBytes()
////                val bitmapImage =  BitmapFactory.decodeByteArray(newImage, 0, newImage!!.size)
////                val newWarrior = Warrior(
////                    bitmapImage,
////                    "Полковник",
////                    "Олександр Аніщенко",
////                    "Oleksandr Anishenko",
////                    "Олександр Григорович Аніщенко",
////                    "Oleksandr Hryhorovych Onishenko",
////                    11,
////                    11,
////                    11,
////                    "Descr",
////                    listOf(bitmapImage, bitmapImage),
////                    listOf(bitmapImage, bitmapImage)
////                )
////                recycleViewWarriorsAddViewModel.addWarrior(newWarrior)
////            }
////        )
////        binding.gallery.setOnClickListener {
////            galleryImage.launch("image/*")
////        }newImage = requireActivity().contentResolver.openInputStream(it!!)?.readBytes()
//                val bitmapImage =  BitmapFactory.decodeByteArray(newImage, 0, newImage!!.size)
//                val newWarrior = Warrior(
//                    bitmapImage,
//                    "Полковник",
//                    "Олександр Аніщенко",
//                    "Oleksandr Anishenko",
//                    "Олександр Григорович Аніщенко",
//                    "Oleksandr Hryhorovych Onishenko",
//                    11,
//                    11,
//                    11,
//                    "Descr",
//                    listOf(bitmapImage, bitmapImage),
//                    listOf(bitmapImage, bitmapImage)
//                )
//                recycleViewWarriorsAddViewModel.addWarrior(newWarrior)
//            }
//        )
//        binding.gallery.setOnClickListener {
//            galleryImage.launch("image/*")
//        }


