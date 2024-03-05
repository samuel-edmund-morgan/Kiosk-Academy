package com.morgandev.kioskacademy.presentation.recyclerViewFragment.adminRecyclerView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.morgandev.kioskacademy.R
import com.morgandev.kioskacademy.databinding.FragmentRecyclerViewWarriorsAddBinding
import com.morgandev.kioskacademy.databinding.FragmentRecyclerViewWarriorsBinding
import com.morgandev.kioskacademy.presentation.recyclerViewFragment.RecyclerViewWarriorsViewModel

class RecyclerViewWarriorsAddFragment : Fragment() {

    private val recycleViewWarriorsAddViewModel: RecyclerViewWarriorsViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[RecyclerViewWarriorsViewModel::class.java]
    }


    private var _binding: FragmentRecyclerViewWarriorsAddBinding? = null
    private val binding: FragmentRecyclerViewWarriorsAddBinding
        get() = _binding ?:  throw RuntimeException("FragmentRecyclerViewWarriorsAddBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentRecyclerViewWarriorsAddBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        fun newInstance() = RecyclerViewWarriorsAddFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}