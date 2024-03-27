package com.morgandev.kioskacademy.presentation.recyclerViewFragment


import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.morgandev.kioskacademy.R
import com.morgandev.kioskacademy.databinding.FragmentRecyclerViewWarriorsBinding
import com.morgandev.kioskacademy.domain.entities.VideoData
import com.morgandev.kioskacademy.domain.entities.Warrior
import com.morgandev.kioskacademy.presentation.EventObserver
import com.morgandev.kioskacademy.presentation.recyclerViewFragment.recyclerViewData.RecyclerViewWarriorsAdapter
import com.morgandev.kioskacademy.presentation.welcomeFragment.WelcomeScreenFragmentDirections
import java.io.File

class RecyclerViewWarriorsFragment : Fragment() {

    private val recycleViewWarriorsViewModel: RecyclerViewWarriorsViewModel by activityViewModels()

    private lateinit var recyclerViewWarriorsAdapter: RecyclerViewWarriorsAdapter


    private var _binding: FragmentRecyclerViewWarriorsBinding? = null
    private val binding: FragmentRecyclerViewWarriorsBinding
        get() = _binding ?: throw RuntimeException("FragmentRecyclerViewWarriorsBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerViewWarriorsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackBtnPressed()
        observeKeyDownEventChanges()
        setupRecyclerView()
        submitListObserver()

    }

    private fun submitListObserver() {
        recycleViewWarriorsViewModel.videoList.observe(viewLifecycleOwner) {
            recyclerViewWarriorsAdapter.submitList(it)
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {

        with(binding.rvWarriors) {
            val lManager = FlexboxLayoutManager(context)
            lManager.flexWrap = FlexWrap.WRAP
            lManager.flexDirection = FlexDirection.ROW
            lManager.justifyContent = JustifyContent.CENTER
            lManager.alignItems = AlignItems.CENTER
            binding.arrowDown.setOnClickListener {
                val position = lManager.findLastVisibleItemPosition()
                smoothScrollToPosition(position + 4)
            }
            binding.arrowUp.setOnClickListener {
                val swipePosition = if (lManager.findFirstVisibleItemPosition() > 3)
                    lManager.findFirstVisibleItemPosition() - 4
                else 0
                smoothScrollToPosition(swipePosition)
            }
            layoutManager = lManager
            recyclerViewWarriorsAdapter = RecyclerViewWarriorsAdapter()
            adapter = recyclerViewWarriorsAdapter
            recycledViewPool.setMaxRecycledViews(
                RecyclerViewWarriorsAdapter.VIEW_TYPE_ENABLED,
                RecyclerViewWarriorsAdapter.MAX_POOL_SIZE
            )
        }
        setupClickListener()

    }

    private fun observeKeyDownEventChanges() {
        recycleViewWarriorsViewModel.keyEvent.observe(viewLifecycleOwner, EventObserver {
            launchRecyclerViewWarriorsAddFragment()
        }
        )
    }

    private fun launchRecyclerViewWarriorsAddFragment() {
        findNavController().navigate(R.id.action_recyclerViewWarriorsFragment_to_recyclerViewWarriorsAddFragment)
    }

    private fun setupClickListener() {
        recyclerViewWarriorsAdapter.onWarriorClickListener = {
            fileName: String , filePosition: Int ->
            //Here launch dialog with videos
            setupVideoDialogBox(fileName, filePosition)
        }
    }

    private fun setupVideoDialogBox(videoName: String, position: Int) {

        val videoFileNameValue = videoName
        val vidFilePath = requireContext().filesDir

        val dialog = Dialog(requireContext())

        dialog.setContentView(R.layout.video_dialog)
        val videoView = dialog.findViewById<VideoView>(R.id.videoView)
        val frameView = dialog.findViewById<FrameLayout>(R.id.frameView)

        videoView.setVideoURI(File("${vidFilePath}/${videoFileNameValue}/${videoFileNameValue}").toUri())
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
            binding.rvWarriors.smoothScrollToPosition(position + 2)
            binding.rvWarriors.findViewHolderForAdapterPosition(position + 1)?.itemView?.performClick()
        }

        //swipe left
        val leftBtnVideo = dialog.findViewById<ImageView>(R.id.arrowLeftFullVideo)
        leftBtnVideo.setOnClickListener {
            dialog.dismiss()
            if (position > 1){
                binding.rvWarriors.smoothScrollToPosition(position-2)
            }
            binding.rvWarriors.findViewHolderForAdapterPosition(position - 1)?.itemView?.performClick()
        }
        //cross close
        val crossClose = dialog.findViewById<ImageView>(R.id.crossVideo)
        crossClose.setOnClickListener {
            dialog.dismiss()
        }


        dialog.show()
    }



    private fun launchRecyclerViewWarriorsFragment(warrior : Warrior){
        findNavController().navigate(RecyclerViewWarriorsFragmentDirections
            .actionRecyclerViewWarriorsFragmentToDetailedScreenFragment(warrior))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onBackBtnPressed(){
        binding.brandingIv.setOnClickListener {
            val mIntent = requireActivity().intent
            requireActivity().finish()
            startActivity(mIntent)



            //findNavController().popBackStack()

        }
        binding.recyclerViewMessage.setOnClickListener {
            val mIntent = requireActivity().intent
            requireActivity().finish()
            startActivity(mIntent)
            //findNavController().popBackStack()
        }
//        binding.ssuEmblem.setOnClickListener {
//            findNavController().popBackStack()
//        }
    }


}