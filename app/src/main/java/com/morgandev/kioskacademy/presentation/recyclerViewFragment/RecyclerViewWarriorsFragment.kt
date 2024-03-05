package com.morgandev.kioskacademy.presentation.recyclerViewFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.morgandev.kioskacademy.R
import com.morgandev.kioskacademy.databinding.FragmentRecyclerViewWarriorsBinding
import com.morgandev.kioskacademy.databinding.FragmentWelcomeScreenBinding
import com.morgandev.kioskacademy.presentation.welcomeFragment.WelcomeFragmentViewModel

class RecyclerViewWarriorsFragment : Fragment() {


    private val recycleViewWarriorsViewModel: RecyclerViewWarriorsViewModel by activityViewModels()


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
    }

    companion object {
        fun newInstance() = RecyclerViewWarriorsFragment()
    }

    private fun observeKeyDownEventChanges() {
        recycleViewWarriorsViewModel.keyEvent.observe(viewLifecycleOwner) {
            launchRecyclerViewWarriorsAddFragment()
        }
    }

    private fun launchRecyclerViewWarriorsAddFragment(){
        findNavController().navigate(R.id.action_recyclerViewWarriorsFragment_to_recyclerViewWarriorsAddFragment)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}