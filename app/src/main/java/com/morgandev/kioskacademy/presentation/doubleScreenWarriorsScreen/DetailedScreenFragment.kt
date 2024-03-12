package com.morgandev.kioskacademy.presentation.doubleScreenWarriorsScreen

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.morgandev.kioskacademy.R
import com.morgandev.kioskacademy.databinding.CustomDialogBinding
import com.morgandev.kioskacademy.databinding.FragmentDetailedScreenBinding
import com.morgandev.kioskacademy.domain.entities.Warrior
import com.morgandev.kioskacademy.presentation.doubleScreenWarriorsScreen.namesRecyclerView.RecyclerViewWarriorsNamesAdapter
import com.morgandev.kioskacademy.presentation.doubleScreenWarriorsScreen.photoGalleryRecyclerView.PhotoGalleryRecyclerViewAdapter
import com.morgandev.kioskacademy.presentation.recyclerViewFragment.RecyclerViewWarriorsViewModel
import java.io.File


class DetailedScreenFragment : Fragment() {

    private val detailedScreenFragmentViewModel: RecyclerViewWarriorsViewModel by activityViewModels()


    private lateinit var recyclerViewWarriorsNamesAdapter: RecyclerViewWarriorsNamesAdapter
    private lateinit var photoGalleryRecyclerViewAdapter: PhotoGalleryRecyclerViewAdapter

    private var _binding: FragmentDetailedScreenBinding? = null
    private val binding: FragmentDetailedScreenBinding
        get() = _binding ?: throw RuntimeException("FragmentDetailedScreenBinding == null")

    private var _args: DetailedScreenFragmentArgs? = null
    private val args: DetailedScreenFragmentArgs
        get() = _args ?: throw RuntimeException("DetailedScreenFragmentArgs == null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailedScreenBinding.inflate(inflater, container, false)
        _args = DetailedScreenFragmentArgs.fromBundle(requireArguments())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val warrior = args.warrior
        //val galleryFolderName = args.warrior.profilePicture

        submitListObserver()
        setupRecyclerView()
        setupPhotoGalleryRecyclerView(warrior)
        setupViewsOfDetailedInfo(warrior)

        recyclerViewWarriorsNamesAdapter.onWarriorClickListener = {
            setupViewsOfDetailedInfo(it)
            setupPhotoGalleryRecyclerView(it)
        }
        photoGalleryRecyclerViewAdapter.onPhotoClickListener = {
            setupDialogBox(warrior, it)
        }

    }

    private fun setupDialogBox(warrior: Warrior, imageName: String) {


        val profilePictureValue = warrior.profilePicture
        val picFilePath = requireContext().filesDir

        val dialog = Dialog(requireContext())

        dialog.setContentView(R.layout.custom_dialog)

        val imageView = dialog.findViewById<ImageView>(R.id.dialogImageView)

        Glide.with(requireContext())
            .load(File("${picFilePath}/${profilePictureValue}/${imageName}"))
            .into(imageView)
            .waitForLayout()
        dialog.show()
    }

    private fun setupViewsOfDetailedInfo(warrior: Warrior) {
        setupViewAndVisibility(warrior, warrior.profilePicture, binding.profilePictureIv, true)
        setupViewAndVisibility(warrior, warrior.departmentEmblem, binding.departmentEmblem, true)
        setupViewAndVisibility(warrior, warrior.rank, binding.rank, false)
        setupViewAndVisibility(
            warrior,
            warrior.fullNameUA.replace(' ', '\n'),
            binding.fullName,
            false
        )
        setupViewAndVisibility(
            warrior,
            "${warrior.dateBirth} - ${warrior.dateDied}",
            binding.dates,
            false
        )

        //edit description for good paragraphs
        setupViewAndVisibility(warrior, warrior.description, binding.descriptionTv, false)
        //setup photo gallery
        //setup video gallery


    }

    private fun setupViewAndVisibility(
        warrior: Warrior,
        dbField: String,
        view: View,
        isImage: Boolean
    ) {
        val profilePictureValue = warrior.profilePicture
        val picFilePath = requireContext().filesDir
        if (dbField != "") {
            if (isImage) {
                Glide.with(requireContext())
                    .load(File("${picFilePath}/${profilePictureValue}/${dbField}"))
                    .into((view as ImageView))
                    .waitForLayout()
                view.visibility = View.VISIBLE
            } else {
                (view as TextView).text = dbField
                view.visibility = View.VISIBLE
            }
        } else {
            view.visibility = View.GONE
        }

    }

    private fun submitListObserver() {
        detailedScreenFragmentViewModel.warriorList.observe(viewLifecycleOwner) {
            recyclerViewWarriorsNamesAdapter.submitList(it)
            binding.progressBarNames.visibility = View.GONE
        }
    }
//    private fun submitPhotoGalleryObserver() {
//        detailedScreenFragmentViewModel.warriorList.observe(viewLifecycleOwner) {
//            recyclerViewWarriorsNamesAdapter.submitList(it.)
//            binding.progressBarNames.visibility = View.GONE
//        }
//    }

    private fun setupRecyclerView() {

        with(binding.recyclerViewWarriorFullnames) {
            val lManager = LinearLayoutManager(context)

            binding.arrowDown.setOnClickListener {
                val position = lManager.findLastVisibleItemPosition()
                smoothScrollToPosition(position + 1)
            }
            binding.arrowUp.setOnClickListener {
                val swipePosition = if (lManager.findFirstVisibleItemPosition() > 0)
                    lManager.findFirstVisibleItemPosition() - 1
                else 0
                smoothScrollToPosition(swipePosition)
            }
            layoutManager = lManager
            recyclerViewWarriorsNamesAdapter = RecyclerViewWarriorsNamesAdapter()
            adapter = recyclerViewWarriorsNamesAdapter
            recycledViewPool.setMaxRecycledViews(
                RecyclerViewWarriorsNamesAdapter.VIEW_TYPE_ENABLED,
                RecyclerViewWarriorsNamesAdapter.MAX_POOL_SIZE
            )
        }
    }

    private fun setupPhotoGalleryRecyclerView(warrior: Warrior) {

        with(binding.photoGalleryRv) {
            val lManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

//            binding.arrowDown.setOnClickListener {
//                val position = lManager.findLastVisibleItemPosition()
//                smoothScrollToPosition(position + 1)
//            }
//            binding.arrowUp.setOnClickListener {
//                val swipePosition = if (lManager.findFirstVisibleItemPosition() > 0)
//                    lManager.findFirstVisibleItemPosition() - 1
//                else 0
//                smoothScrollToPosition(swipePosition)
//            }

            layoutManager = lManager
            photoGalleryRecyclerViewAdapter =
                PhotoGalleryRecyclerViewAdapter(warrior.profilePicture)
            adapter = photoGalleryRecyclerViewAdapter

            recycledViewPool.setMaxRecycledViews(
                RecyclerViewWarriorsNamesAdapter.VIEW_TYPE_ENABLED,
                RecyclerViewWarriorsNamesAdapter.MAX_POOL_SIZE
            )
            val listOfPhotos = warrior.photos.split(",").map { it.trim() }
            photoGalleryRecyclerViewAdapter.submitList(listOfPhotos)
            photoGalleryRecyclerViewAdapter.onPhotoClickListener = {
                setupDialogBox(warrior, it)
            }
        }
    }


}