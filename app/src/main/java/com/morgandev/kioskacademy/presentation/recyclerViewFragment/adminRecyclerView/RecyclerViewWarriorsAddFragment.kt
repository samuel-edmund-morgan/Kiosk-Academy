package com.morgandev.kioskacademy.presentation.recyclerViewFragment.adminRecyclerView

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.morgandev.kioskacademy.databinding.FragmentRecyclerViewWarriorsAddBinding
import com.morgandev.kioskacademy.presentation.EventObserver
import com.morgandev.kioskacademy.presentation.recyclerViewFragment.RecyclerViewWarriorsViewModel
import java.io.BufferedOutputStream
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
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

            choseAnyFileClickListener(
                profileTv,
                profileBtn,
                false
            )

            choseAnyFileClickListener(
                profilePhotoDetailedTv,
                profilePhotoDetailedBtn,
                false
            )

            choseAnyFileClickListener(
                emblemTv,
                emblemBtn,
                false
            )

            choseDateClickListener(birthTv, birthBtn)
            choseDateClickListener(deathTv, deathBtn)

            choseAnyFileClickListener(
                photoTv,
                photoBtn,
                true
            )

            choseAnyFileClickListener(
                videoTv,
                videoBtn,
                true
            )

            choseAnyFileClickListener(
                descriptionTv,
                descriptionBtn,
                false
            )

            checkInputData()

            saveDataBtn.setOnClickListener {
                recycleViewWarriorsAddViewModel.copyFromCacheToFiles(binding)
            }
        }
    }

    private fun observeViewModel() {
        recycleViewWarriorsAddViewModel.shouldCloseScreen.observe(
            viewLifecycleOwner,
            EventObserver {
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

    private fun choseAnyFileClickListener(
        textView: TextView,
        button: Button,
        multipleFiles: Boolean
    ) {
        val contract = if (!multipleFiles) ActivityResultContracts.OpenDocument() else
            ActivityResultContracts.OpenMultipleDocuments()
        val launcher = registerForActivityResult(contract) { uri ->
            if (uri != null) {
                val listOfFileNames = mutableListOf<String>()
                if (uri is List<*> && uri.isNotEmpty()) {
                    for (uriItem in uri) {
                        if (uriItem is Uri) {
                            val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                            context?.contentResolver?.takePersistableUriPermission(uriItem, flag)

                            var fileName = File(uriItem.path.toString()).name
                            fileName = fileName.substring(fileName.indexOf(':') + 1)

                            val fullFileTmpPath = File(context?.cacheDir, fileName).absolutePath
                            requireActivity().contentResolver.openInputStream(uriItem).use {
                                writeToFile(it, fullFileTmpPath)
                            }


                            listOfFileNames.add(fileName)
                        }
                    }
                    textView.text = listOfFileNames.toString()
                        .replace("[", "")
                        .replace("]", "")

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
                        .replace("[", "")
                        .replace("]", "")
                        .substring(fileName.indexOf(':') + 1)
                        .trim()
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
            launcher.launch(arrayOf("*/*"))
        }
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

    private fun checkInputData() {
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
                    val formattedDetailedPhoto = profilePhotoDetailedTv.text.toString().trim()
                    val formattedNameSurname = nameSurnameEt.text.toString().trim()
                    val formattedSurnameNamePatronim = surnameNamePatronimEt.text.toString().trim()
                    val formattedBirthDate = birthTv.text.toString().trim()
                    val formattedDeathDate = deathTv.text.toString().trim()
                    val formattedDescription = descriptionTv.text.toString().trim()

                    saveDataBtn.isEnabled = formattedPhotoName.isNotEmpty() &&
                            formattedNameSurname.isNotEmpty() && formattedSurnameNamePatronim.isNotEmpty() &&
                            formattedBirthDate.isNotEmpty() && formattedDeathDate.isNotEmpty() &&
                            formattedDescription.isNotEmpty() && formattedDetailedPhoto.isNotEmpty()
                }

                override fun afterTextChanged(s: Editable?) {}
            }
            profileTv.addTextChangedListener(textWatcher)
            profilePhotoDetailedTv.addTextChangedListener(textWatcher)
            nameSurnameEt.addTextChangedListener(textWatcher)
            surnameNamePatronimEt.addTextChangedListener(textWatcher)
            birthTv.addTextChangedListener(textWatcher)
            deathTv.addTextChangedListener(textWatcher)
            descriptionTv.addTextChangedListener(textWatcher)
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

