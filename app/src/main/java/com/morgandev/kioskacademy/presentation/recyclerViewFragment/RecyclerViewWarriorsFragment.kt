package com.morgandev.kioskacademy.presentation.recyclerViewFragment

import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.morgandev.kioskacademy.R
import com.morgandev.kioskacademy.databinding.FragmentRecyclerViewWarriorsBinding
import com.morgandev.kioskacademy.presentation.EventObserver
import com.morgandev.kioskacademy.presentation.recyclerViewFragment.recyclerViewData.RecyclerViewWarriorsAdapter

class RecyclerViewWarriorsFragment : Fragment() {

    private val recycleViewWarriorsViewModel: RecyclerViewWarriorsViewModel by activityViewModels()

    private lateinit var recyclerViewWarriorsAdapter: RecyclerViewWarriorsAdapter


    private var _binding: FragmentRecyclerViewWarriorsBinding? = null
    private val binding: FragmentRecyclerViewWarriorsBinding
        get() = _binding ?:  throw RuntimeException("FragmentRecyclerViewWarriorsBinding == null")




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentRecyclerViewWarriorsBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeKeyDownEventChanges()
        setupRecyclerView()
        submitListObserver()

    }

    companion object {
        fun newInstance() = RecyclerViewWarriorsFragment()
    }

    private fun submitListObserver(){
        recycleViewWarriorsViewModel.warriorList.observe(viewLifecycleOwner) {
            recyclerViewWarriorsAdapter.submitList(it)
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvWarriors) {
            recyclerViewWarriorsAdapter = RecyclerViewWarriorsAdapter()
            adapter = recyclerViewWarriorsAdapter
            recycledViewPool.setMaxRecycledViews(
                RecyclerViewWarriorsAdapter.VIEW_TYPE_ENABLED,
                RecyclerViewWarriorsAdapter.MAX_POOL_SIZE
            )
        }

    }

    private fun observeKeyDownEventChanges() {
                recycleViewWarriorsViewModel.keyEvent.observe(viewLifecycleOwner, EventObserver
                { result ->
                    Toast.makeText(activity, "Tedddst", Toast.LENGTH_LONG).show()
                    launchRecyclerViewWarriorsAddFragment()
                }
                )


    }

    private fun launchRecyclerViewWarriorsAddFragment(){

            findNavController().navigate(R.id.action_recyclerViewWarriorsFragment_to_recyclerViewWarriorsAddFragment)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}