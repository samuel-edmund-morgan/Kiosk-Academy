package com.morgandev.kioskacademy.presentation.recyclerViewFragment.adminRecyclerView

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.morgandev.kioskacademy.presentation.EventObserver
import com.morgandev.kioskacademy.presentation.recyclerViewFragment.RecyclerViewWarriorsViewModel
import java.io.ByteArrayInputStream
import java.io.File
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RecyclerViewWarriorsAddFragment : Fragment() {

    private val recycleViewWarriorsAddViewModel: RecyclerViewWarriorsViewModel by activityViewModels()

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var _binding: FragmentRecyclerViewWarriorsAddBinding? = null
    private val binding: FragmentRecyclerViewWarriorsAddBinding
        get() = _binding ?: throw RuntimeException("FragmentRecyclerViewWarriorsAddBinding == null")

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
    ): View {
        _binding = FragmentRecyclerViewWarriorsAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()

        with(binding) {

            choseFileClickListener(
                profileTv,
                profileBtn,
                ActivityResultContracts.PickVisualMedia.ImageOnly,
                1
            )

            choseFileClickListener(
                emblemTv,
                emblemBtn,
                ActivityResultContracts.PickVisualMedia.ImageOnly,
                1
            )

            choseDateClickListener(birthTv, birthBtn)
            choseDateClickListener(deathTv, deathBtn)

            choseFileClickListener(
                photoTv,
                photoBtn,
                ActivityResultContracts.PickVisualMedia.ImageOnly,
                15
            )

            choseFileClickListener(
                videoTv,
                videoBtn,
                ActivityResultContracts.PickVisualMedia.VideoOnly,
                5
            )
            checkInputData()
            saveDataBtn.setOnClickListener {
                recycleViewWarriorsAddViewModel.copyFromCacheToFiles(binding)



                //4) MAke everything above in First fragment to makeeditable emblem, text and image of year

            }
        }
    }
    private fun observeViewModel() {
        recycleViewWarriorsAddViewModel.shouldCloseScreen.observe(viewLifecycleOwner, EventObserver {
            onEditingFinishedListener.onEditingFinished()
        })

    }
    private fun choseDateClickListener(textView: TextView, button: Button) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = context?.let {
            DatePickerDialog(
                it, { _, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, monthOfYear, dayOfMonth)
                    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("uk", "UA"))
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
    private fun choseFileClickListener(
        textView: TextView,
        button: Button,
        visualMediaType: ActivityResultContracts.PickVisualMedia.VisualMediaType,
        fileCount: Int
    ) {
        if (fileCount < 1) throw IllegalArgumentException()

        val contract = if (fileCount == 1) ActivityResultContracts.PickVisualMedia() else
            ActivityResultContracts.PickMultipleVisualMedia(fileCount)
        val pickMedia = registerForActivityResult(contract) { uri ->
            if (uri != null) {
                val listOfFileNames = mutableListOf<String>()
                if (uri is List<*> && uri.isNotEmpty()) {
                    for (uriItem in uri) {
                        if (uriItem is Uri) {
                            val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                            context?.contentResolver?.takePersistableUriPermission(uriItem, flag)

                            val fileName = File(uriItem.path.toString()).name
                            val fullFileTmpPath = File(context?.cacheDir, fileName)
                            var newVideo: ByteArray?
                            requireActivity().contentResolver.openInputStream(uriItem).use {
                                newVideo = it?.readBytes()
                                it?.close()
                            }
                            val inputStream = ByteArrayInputStream(newVideo)
                            inputStream.use { input ->
                                fullFileTmpPath.outputStream().use { output ->
                                    input.copyTo(output)
                                }
                            }
                            listOfFileNames.add(fileName)
                        }
                    }
                    textView.text = listOfFileNames.toString()
                        .replace("[","")
                        .replace("]","")
                } else if (uri is Uri) {
                    val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    context?.contentResolver?.takePersistableUriPermission(uri, flag)


                    val fileName = File(uri.path.toString()).name


                    val fullFileTmpPath = File(context?.cacheDir, fileName)
                    val newVideo: ByteArray?
                    requireActivity().contentResolver.openInputStream(uri).use {
                        newVideo = it?.readBytes()
                        it?.close()
                    }
                    val inputStream = ByteArrayInputStream(newVideo)
                    inputStream.use { input ->
                        fullFileTmpPath.outputStream().use { output ->
                            input.copyTo(output)
                        }
                    }
                    textView.text = fileName
                        .replace("[","")
                        .replace("]","")
                } else {
                    textView.text = ""
                    Toast.makeText(context, "Медіа не обрано!", Toast.LENGTH_LONG).show()
                }
            } else {
                textView.text = ""
                Toast.makeText(context, "Медіа не обрано!", Toast.LENGTH_LONG).show()
            }
        }
        button.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(visualMediaType))
        }
    }
    private fun checkInputData(){
        with(binding) {
            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val formattedPhotoName = profileTv.text.toString().trim()
                    val formattedNameSurname = nameSurnameEt.text.toString().trim()
                    val formattedSurnameNamePatronim = surnameNamePatronimEt.text.toString().trim()
                    val formattedBirthDate = birthTv.text.toString().trim()
                    val formattedDeathDate = deathTv.text.toString().trim()
                    val formattedDescription = descriptionEt.text.toString().trim()
                    val formattedPhotoList = photoTv.text.toString().trim()
                    saveDataBtn.isEnabled = formattedPhotoName.isNotEmpty() &&
                            formattedNameSurname.isNotEmpty() && formattedSurnameNamePatronim.isNotEmpty() &&
                            formattedBirthDate.isNotEmpty() && formattedDeathDate.isNotEmpty() &&
                            formattedDescription.isNotEmpty() && formattedPhotoList.isNotEmpty()
                }
                override fun afterTextChanged(s: Editable?) {}
            }
            profileTv.addTextChangedListener(textWatcher)
            nameSurnameEt.addTextChangedListener(textWatcher)
            surnameNamePatronimEt.addTextChangedListener(textWatcher)
            birthTv.addTextChangedListener(textWatcher)
            deathTv.addTextChangedListener(textWatcher)
            photoTv.addTextChangedListener(textWatcher)
            descriptionEt.addTextChangedListener(textWatcher)
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

