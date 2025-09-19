package com.morgandev.kioskacademy.presentation.doubleScreenWarriorsScreen

import android.app.Dialog
import android.os.Bundle
import android.text.SpannableString
import android.text.style.LeadingMarginSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.MediaController
import android.widget.ScrollView
import android.widget.TextView
import android.widget.VideoView
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class DetailedScreenFragment : Fragment() {

    private val viewModel: RecyclerViewWarriorsViewModel by activityViewModels()

    private lateinit var namesAdapter: RecyclerViewWarriorsNamesAdapter
    private lateinit var photoAdapter: PhotoGalleryRecyclerViewAdapter
    private lateinit var videoAdapter: VideoGalleryRecyclerViewAdapter

    private var _binding: FragmentDetailedScreenBinding? = null
    private val binding: FragmentDetailedScreenBinding get() = _binding!!

    private var currentWarrior: Warrior? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailedScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = DetailedScreenFragmentArgs.fromBundle(requireArguments())
        currentWarrior = args.warrior

        initAdapters(args.warrior)
        setupNamesRecycler()
        setupPhotoRecycler()
        setupVideoRecycler()
        observeWarriorList()
        observeDeleteEvents(args.warrior)
        bindWarrior(args.warrior)
        updatePhotoGallery(args.warrior)
        updateVideoGallery(args.warrior)
        setupCallbacks()
        onBackBtnPressed()
        binding.detailedInfoScrollView.fullScroll(ScrollView.FOCUS_UP)
    }

    private fun initAdapters(warrior: Warrior) {
        namesAdapter = RecyclerViewWarriorsNamesAdapter()
        photoAdapter = PhotoGalleryRecyclerViewAdapter(warrior.profilePicture)
        videoAdapter = VideoGalleryRecyclerViewAdapter(warrior.profilePicture)
    }

    private fun setupCallbacks() {
        namesAdapter.onWarriorClickListener = { w ->
            currentWarrior = w
            bindWarrior(w)
            updatePhotoGallery(w)
            updateVideoGallery(w)
            binding.detailedInfoScrollView.fullScroll(ScrollView.FOCUS_UP)
        }
        photoAdapter.onPhotoClickListener = { name, pos ->
            currentWarrior?.let { setupPhotoDialog(it, name, pos) }
        }
        videoAdapter.onVideoClickListener = { name, pos ->
            currentWarrior?.let { setupVideoDialog(it, name, pos) }
        }
    }

    private fun bindWarrior(warrior: Warrior) {
        loadImage(warrior.profileDetailedPhoto, binding.profilePictureIv, warrior.profilePicture)
        loadImage(warrior.departmentEmblem, binding.departmentEmblem, warrior.profilePicture)
        setTextOrHide(binding.rank, warrior.rank)
        setTextOrHide(binding.fullName, warrior.fullNameUA)
        binding.dates.visibility = View.GONE
        loadDescriptionAsync(warrior)
        binding.detailedInfoScrollView.smoothScrollTo(0,0)
    }

    private fun loadDescriptionAsync(warrior: Warrior) {
        val dir = requireContext().filesDir
        val file = File(dir, "${warrior.profilePicture}/${warrior.description}")
        lifecycleScope.launch(Dispatchers.IO) {
            val text = runCatching { file.readText() }.getOrDefault("no description")
            val span = SpannableString(text).apply {
                setSpan(LeadingMarginSpan.Standard(110,0),0,length,SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            withContext(Dispatchers.Main) { binding.descriptionTv.text = span }
        }
    }

    private fun setTextOrHide(view: TextView, value: String) {
        if (value.isBlank()) view.visibility = View.GONE else { view.text = value; view.visibility = View.VISIBLE }
    }

    private fun loadImage(name: String, target: ImageView, folder: String) {
        if (name.isBlank()) { target.visibility = View.GONE; return }
        val file = File(requireContext().filesDir, "$folder/$name")
        Glide.with(this).load(file).centerCrop().into(target)
        target.visibility = View.VISIBLE
    }

    // Always set adapter even if layoutManager provided via XML (previously skipped)
    private fun setupNamesRecycler() {
        val rv = binding.recyclerViewWarriorFullnames
        val lm = rv.layoutManager as? LinearLayoutManager ?: LinearLayoutManager(requireContext()).also { rv.layoutManager = it }
        rv.setHasFixedSize(true)
        binding.arrowDown.setOnClickListener { rv.smoothScrollToPosition(lm.findLastVisibleItemPosition()+1) }
        binding.arrowUp.setOnClickListener { rv.smoothScrollToPosition((lm.findFirstVisibleItemPosition()-1).coerceAtLeast(0)) }
        rv.adapter = namesAdapter
    }

    private fun setupPhotoRecycler() {
        val rv = binding.photoGalleryRv
        val lm = rv.layoutManager as? LinearLayoutManager ?: LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false).also { rv.layoutManager = it }
        rv.setHasFixedSize(true)
        binding.arrowLeft.setOnClickListener { rv.smoothScrollToPosition((lm.findFirstVisibleItemPosition()-1).coerceAtLeast(0)) }
        binding.arrowRight.setOnClickListener { rv.smoothScrollToPosition(lm.findLastVisibleItemPosition()+1) }
        rv.adapter = photoAdapter
    }

    private fun setupVideoRecycler() {
        val rv = binding.videoGalleryRv
        val lm = rv.layoutManager as? LinearLayoutManager ?: LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false).also { rv.layoutManager = it }
        rv.setHasFixedSize(true)
        binding.arrowLeftVideo.setOnClickListener { rv.smoothScrollToPosition((lm.findFirstVisibleItemPosition()-1).coerceAtLeast(0)) }
        binding.arrowRightVideo.setOnClickListener { rv.smoothScrollToPosition(lm.findLastVisibleItemPosition()+1) }
        rv.adapter = videoAdapter
    }

    private fun updatePhotoGallery(warrior: Warrior) {
        val items = warrior.photos.takeIf { it.isNotBlank() }?.split(',')?.map { it.trim().trim('[',']') }?.filter { it.isNotBlank() } ?: emptyList()
        photoAdapter.setWarriorDir(warrior.profilePicture)
        photoAdapter.submitList(items)
        val vis = items.isNotEmpty()
        binding.photoGalleryRv.visibility = if (vis) View.VISIBLE else View.GONE
        binding.arrowLeft.visibility = if (vis) View.VISIBLE else View.GONE
        binding.arrowRight.visibility = if (vis) View.VISIBLE else View.GONE
    }

    private fun updateVideoGallery(warrior: Warrior) {
        val items = warrior.videos.takeIf { it.isNotBlank() }?.split(',')?.map { it.trim().trim('[',']') }?.filter { it.isNotBlank() } ?: emptyList()
        videoAdapter.updateWarriorDir(warrior.profilePicture)
        videoAdapter.submitList(items)
        val vis = items.isNotEmpty()
        binding.videoGalleryRv.visibility = if (vis) View.VISIBLE else View.GONE
        binding.arrowLeftVideo.visibility = if (vis) View.VISIBLE else View.GONE
        binding.arrowRightVideo.visibility = if (vis) View.VISIBLE else View.GONE
    }

    private fun setupPhotoDialog(warrior: Warrior, imageName: String, position: Int) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.photo_dialog)
        val iv = dialog.findViewById<ImageView>(R.id.dialogImageView)
        val file = File(requireContext().filesDir, "${warrior.profilePicture}/$imageName")
        Glide.with(this).load(file).centerInside().into(iv)
        dialog.findViewById<ImageView>(R.id.arrowRightFull).setOnClickListener {
            dialog.dismiss(); binding.photoGalleryRv.smoothScrollToPosition(position+2)
            binding.photoGalleryRv.findViewHolderForAdapterPosition(position+1)?.itemView?.performClick()
        }
        dialog.findViewById<ImageView>(R.id.arrowLeftFull).setOnClickListener {
            dialog.dismiss(); if (position>1) binding.photoGalleryRv.smoothScrollToPosition(position-2)
            binding.photoGalleryRv.findViewHolderForAdapterPosition(position-1)?.itemView?.performClick()
        }
        dialog.findViewById<ImageView>(R.id.cross).setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun setupVideoDialog(warrior: Warrior, videoName: String, position: Int) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.video_dialog)
        val videoView = dialog.findViewById<VideoView>(R.id.videoView)
        val frame = dialog.findViewById<FrameLayout>(R.id.frameView)
        val file = File(requireContext().filesDir, "${warrior.profilePicture}/$videoName")
        videoView.setVideoURI(file.toUri())
        videoView.setOnPreparedListener {
            val mc = MediaController(videoView.context)
            val parent = mc.parent as ViewGroup
            parent.removeView(mc)
            frame.addView(mc)
            mc.setMediaPlayer(videoView)
            mc.isEnabled = true
            videoView.setMediaController(mc)
            mc.setAnchorView(videoView)
            videoView.start(); mc.show(0)
            videoView.setOnClickListener { frame.visibility = if (frame.isVisible) View.GONE else View.VISIBLE }
        }
        dialog.findViewById<ImageView>(R.id.arrowRightFullVideo).setOnClickListener {
            dialog.dismiss(); binding.videoGalleryRv.smoothScrollToPosition(position+2)
            binding.videoGalleryRv.findViewHolderForAdapterPosition(position+1)?.itemView?.performClick()
        }
        dialog.findViewById<ImageView>(R.id.arrowLeftFullVideo).setOnClickListener {
            dialog.dismiss(); if (position>1) binding.videoGalleryRv.smoothScrollToPosition(position-2)
            binding.videoGalleryRv.findViewHolderForAdapterPosition(position-1)?.itemView?.performClick()
        }
        dialog.findViewById<ImageView>(R.id.crossVideo).setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun observeWarriorList() {
        viewModel.warriorList.observe(viewLifecycleOwner) { list ->
            namesAdapter.submitList(list) {
                // Auto-select current warrior if present
                currentWarrior?.let { cw ->
                    val idx = list.indexOfFirst { it.id == cw.id && it.id != 0 }
                        .takeIf { it >= 0 } ?: list.indexOfFirst { it.profilePicture == cw.profilePicture }
                    if (idx >= 0) {
                        namesAdapter.selectedPosition = idx
                        namesAdapter.notifyItemChanged(idx)
                        binding.recyclerViewWarriorFullnames.post {
                            binding.recyclerViewWarriorFullnames.scrollToPosition(idx)
                        }
                    }
                }
                binding.progressBarNames.visibility = View.GONE
            }
        }
    }

    private fun observeDeleteEvents(warrior: Warrior) {
        viewModel.keyEvent.observe(viewLifecycleOwner, EventObserver {
            val folder = File(requireContext().filesDir, warrior.profilePicture)
            folder.deleteRecursively()
            viewModel.deleteWarrior(warrior)
        })
    }

    private fun onBackBtnPressed() { binding.backBtnF.setOnClickListener { findNavController().popBackStack() } }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
