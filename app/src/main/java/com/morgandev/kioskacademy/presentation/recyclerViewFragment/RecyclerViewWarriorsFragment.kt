package com.morgandev.kioskacademy.presentation.recyclerViewFragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.morgandev.kioskacademy.presentation.EventObserver
import com.morgandev.kioskacademy.presentation.recyclerViewFragment.recyclerViewData.RecyclerViewWarriorsAdapter

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
        observeKeyDownEventChanges()
        setupRecyclerView()
        submitListObserver()

    }

    private fun submitListObserver() {
        recycleViewWarriorsViewModel.warriorList.observe(viewLifecycleOwner) {
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
            launchRecyclerViewWarriorsFragment(it.id)
            //binding.progressBar.visibility = View.VISIBLE
//                val intent = DetailedWarriorInfoActivity.newIntentEditItem(requireContext(), it.id)
//                startActivity(intent)
        }
    }

    private fun launchRecyclerViewWarriorsFragment(warriorId : Int){
        findNavController().navigate(RecyclerViewWarriorsFragmentDirections
            .actionRecyclerViewWarriorsFragmentToDetailedScreenFragment(warriorId))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}