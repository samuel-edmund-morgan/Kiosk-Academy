package com.morgandev.kioskacademy.presentation.doubleScreenWarriorsScreen

import android.app.Dialog
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.LeadingMarginSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.MediaController
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.morgandev.kioskacademy.R
import com.morgandev.kioskacademy.databinding.FragmentDetailedScreenBinding
import com.morgandev.kioskacademy.domain.entities.Warrior
import com.morgandev.kioskacademy.presentation.EventObserver
import com.morgandev.kioskacademy.presentation.doubleScreenWarriorsScreen.namesRecyclerView.RecyclerViewWarriorsNamesAdapter
import com.morgandev.kioskacademy.presentation.doubleScreenWarriorsScreen.photoGalleryRecyclerView.PhotoGalleryRecyclerViewAdapter
import com.morgandev.kioskacademy.presentation.doubleScreenWarriorsScreen.videoGalleryRecyclerView.VideoGalleryRecyclerViewAdapter
import com.morgandev.kioskacademy.presentation.recyclerViewFragment.RecyclerViewWarriorsViewModel
import java.io.File
import java.nio.charset.Charset


class DetailedScreenFragment : Fragment() {

    private val detailedScreenFragmentViewModel: RecyclerViewWarriorsViewModel by activityViewModels()


    private lateinit var recyclerViewWarriorsNamesAdapter: RecyclerViewWarriorsNamesAdapter
    private lateinit var photoGalleryRecyclerViewAdapter: PhotoGalleryRecyclerViewAdapter
    private lateinit var videoGalleryRecyclerViewAdapter: VideoGalleryRecyclerViewAdapter

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
        _binding = FragmentDetailedScreenBinding.inflate(inflater, container, false)
        _args = DetailedScreenFragmentArgs.fromBundle(requireArguments())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val warrior = args.warrior

        binding.detailedInfoScrollView.fullScroll(ScrollView.FOCUS_UP)

        observeKeyDownEventChanges(warrior)
        submitListObserver()
        setupRecyclerView()
        setupViewsOfDetailedInfo(warrior)
        setupPhotoGallery(warrior)
        setupVideoGallery(warrior)


        recyclerViewWarriorsNamesAdapter.onWarriorClickListener = {
            binding.detailedInfoScrollView.fullScroll(ScrollView.FOCUS_UP)
            setupViewsOfDetailedInfo(it)
            setupPhotoGallery(it)
            setupVideoGallery(it)



        }
        photoGalleryRecyclerViewAdapter.onPhotoClickListener = {
            fileName: String , filePosition: Int ->
            setupPhotoDialogBox(warrior, fileName, filePosition)
            binding.detailedInfoScrollView.fullScroll(ScrollView.FOCUS_UP)


        }
        videoGalleryRecyclerViewAdapter.onVideoClickListener = {
            fileName: String , filePosition: Int ->
            setupVideoDialogBox(warrior, fileName, filePosition)
            binding.detailedInfoScrollView.fullScroll(ScrollView.FOCUS_UP)


        }

        binding.detailedInfoScrollView.fullScroll(ScrollView.FOCUS_UP)
       

    }

    private fun setupVideoGallery(warrior: Warrior) {
        if (warrior.videos != ""){
            setupVideoGalleryRecyclerView(warrior)
            binding.arrowLeftVideo.visibility = View.VISIBLE
            binding.arrowRightVideo.visibility = View.VISIBLE
            binding.videoGalleryRv.visibility = View.VISIBLE
        }
        else{
            setupVideoGalleryRecyclerView(warrior)
            binding.arrowLeftVideo.visibility = View.GONE
            binding.arrowRightVideo.visibility = View.GONE
            binding.videoGalleryRv.visibility = View.GONE
        }
    }

    private fun setupPhotoGallery(warrior: Warrior) {
        if (warrior.photos != ""){
            setupPhotoGalleryRecyclerView(warrior)
            binding.arrowLeft.visibility = View.VISIBLE
            binding.arrowRight.visibility = View.VISIBLE
            binding.photoGalleryRv.visibility = View.VISIBLE
        }
        else{
            setupPhotoGalleryRecyclerView(warrior)
            binding.arrowLeft.visibility = View.GONE
            binding.arrowRight.visibility = View.GONE
            binding.photoGalleryRv.visibility = View.GONE
        }
    }

    private fun setupViewsOfDetailedInfo(warrior: Warrior) {


        setupViewAndVisibility(warrior, warrior.profileDetailedPhoto, binding.profilePictureIv, true)
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

        val profilePictureValue = warrior.profilePicture
        val picFilePath = requireContext().filesDir
        val descriptionFile = File("${picFilePath}/${profilePictureValue}/${warrior.description}")
        val descriptionUri = descriptionFile.toUri()
        val newDescrition: ByteArray?
        requireActivity().contentResolver.openInputStream(descriptionUri).use {
            newDescrition = it?.readBytes()
            it?.close()
        }
        val rawText = newDescrition?.toString(Charsets.UTF_8) ?: "no description"
        val spannable = SpannableString(rawText)
        val span = LeadingMarginSpan.Standard(110, 0)
        spannable.setSpan(span, 0, spannable.count(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.descriptionTv.text = spannable
        binding.detailedInfoScrollView.smoothScrollTo(0,0)
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
                    //.waitForLayout()
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

            binding.arrowLeft.setOnClickListener {
                val swipePosition = if (lManager.findFirstVisibleItemPosition() > 0)
                    lManager.findFirstVisibleItemPosition() - 1
                else 0
                smoothScrollToPosition(swipePosition)
            }

            binding.arrowRight.setOnClickListener {
                val position = lManager.findLastVisibleItemPosition() + 1
                smoothScrollToPosition(position)
            }

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
                fileName: String , filePosition: Int ->
                setupPhotoDialogBox(warrior, fileName, filePosition)
            }

        }
    }
    private fun setupPhotoDialogBox(warrior: Warrior, imageName: String, position: Int) {


        val profilePictureValue = warrior.profilePicture
        val picFilePath = requireContext().filesDir

        val dialog = Dialog(requireContext())

        dialog.setContentView(R.layout.photo_dialog)

        val imageView = dialog.findViewById<ImageView>(R.id.dialogImageView)

        Glide.with(requireContext())
            .load(File("${picFilePath}/${profilePictureValue}/${imageName}"))
            .into(imageView)
            .waitForLayout()


        //ready swipe right
        val rightBtn = dialog.findViewById<ImageView>(R.id.arrowRightFull)
        rightBtn.setOnClickListener {
            dialog.dismiss()
            binding.photoGalleryRv.smoothScrollToPosition(position+2)
            binding.photoGalleryRv.findViewHolderForAdapterPosition(position + 1)?.itemView?.performClick()
        }

        //swipe left
        val leftBtn = dialog.findViewById<ImageView>(R.id.arrowLeftFull)
        leftBtn.setOnClickListener {
            dialog.dismiss()
            if (position > 1){
                binding.photoGalleryRv.smoothScrollToPosition(position-2)
            }
            binding.photoGalleryRv.findViewHolderForAdapterPosition(position - 1)?.itemView?.performClick()
        }

        //cross close
        val crossClose = dialog.findViewById<ImageView>(R.id.cross)
        crossClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
    private fun setupVideoGalleryRecyclerView(warrior: Warrior) {

        with(binding.videoGalleryRv) {
            val lManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

            binding.arrowLeftVideo.setOnClickListener {
                val swipePosition = if (lManager.findFirstVisibleItemPosition() > 0)
                    lManager.findFirstVisibleItemPosition() - 1
                else 0
                smoothScrollToPosition(swipePosition)
            }

            binding.arrowRightVideo.setOnClickListener {
                val position = lManager.findLastVisibleItemPosition() + 1
                smoothScrollToPosition(position)
            }

            layoutManager = lManager
            videoGalleryRecyclerViewAdapter =
                VideoGalleryRecyclerViewAdapter(warrior.profilePicture)
            adapter = videoGalleryRecyclerViewAdapter

            recycledViewPool.setMaxRecycledViews(
                VideoGalleryRecyclerViewAdapter.VIEW_TYPE_ENABLED,
                VideoGalleryRecyclerViewAdapter.MAX_POOL_SIZE
            )
            val listOfVideos = warrior.videos.split(",").map { it.trim() }
            videoGalleryRecyclerViewAdapter.submitList(listOfVideos)
            videoGalleryRecyclerViewAdapter.onVideoClickListener = {
                fileName: String , filePosition: Int ->
                setupVideoDialogBox(warrior, fileName, filePosition)
            }
        }
    }
    private fun setupVideoDialogBox(warrior: Warrior, videoName: String, position: Int) {

        val profilePictureValue = warrior.profilePicture
        val vidFilePath = requireContext().filesDir

        val dialog = Dialog(requireContext())

        dialog.setContentView(R.layout.video_dialog)
        val videoView = dialog.findViewById<VideoView>(R.id.videoView)
        val frameView = dialog.findViewById<FrameLayout>(R.id.frameView)

        videoView.setVideoURI(File("${vidFilePath}/${profilePictureValue}/${videoName}").toUri())
        videoView.setOnPreparedListener {
            val mediaController = MediaController(videoView.context)
            mediaController.requestFocus()
            val parent = mediaController.parent as ViewGroup
            parent.removeView(mediaController)
            frameView.addView(mediaController)
            mediaController.setMediaPlayer(videoView)
            mediaController.isEnabled = true
            videoView.setMediaController(mediaController)
            mediaController.setAnchorView(videoView)
            videoView.start()
            mediaController.show(0)
            videoView.setOnClickListener {
                frameView.visibility = if (frameView.isVisible) View.GONE else View.VISIBLE
            }
        }
        //ready swipe right
        val rightBtnVideo = dialog.findViewById<ImageView>(R.id.arrowRightFullVideo)
        rightBtnVideo.setOnClickListener {
            dialog.dismiss()
            binding.videoGalleryRv.smoothScrollToPosition(position + 2)
            binding.videoGalleryRv.findViewHolderForAdapterPosition(position + 1)?.itemView?.performClick()
        }

        //swipe left
        val leftBtnVideo = dialog.findViewById<ImageView>(R.id.arrowLeftFullVideo)
        leftBtnVideo.setOnClickListener {
            dialog.dismiss()
            if (position > 1){
                binding.videoGalleryRv.smoothScrollToPosition(position-2)
            }
            binding.videoGalleryRv.findViewHolderForAdapterPosition(position - 1)?.itemView?.performClick()
        }
        //cross close
        val crossClose = dialog.findViewById<ImageView>(R.id.crossVideo)
        crossClose.setOnClickListener {
            dialog.dismiss()
        }


        dialog.show()
    }

    private fun observeKeyDownEventChanges(warrior: Warrior) {
        detailedScreenFragmentViewModel.keyEvent.observe(viewLifecycleOwner, EventObserver {
            val profilePictureValue = warrior.profilePicture
            val picFilePath = requireContext().filesDir
            val directoryToDelete = File("${picFilePath}/${profilePictureValue}")
            directoryToDelete.deleteRecursively()

            detailedScreenFragmentViewModel.deleteWarrior(warrior)
            //Toast.makeText(context, "${warrior.toString()}", Toast.LENGTH_LONG).show()
        }
        )
    }


}