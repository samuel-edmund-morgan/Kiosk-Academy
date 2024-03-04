package com.morgandev.kioskacademy.presentation.recyclerViewFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.morgandev.kioskacademy.R
import com.morgandev.kioskacademy.databinding.FragmentRecyclerViewWarriorsBinding
import com.morgandev.kioskacademy.databinding.FragmentWelcomeScreenBinding

class RecyclerViewWarriorsFragment : Fragment() {

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

    companion object {
        fun newInstance() = RecyclerViewWarriorsFragment()
    }
}