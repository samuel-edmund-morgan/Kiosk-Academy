package com.morgandev.kioskacademy.presentation.doubleScreenWarriorsScreen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.morgandev.kioskacademy.R
import com.morgandev.kioskacademy.databinding.FragmentDetailedScreenBinding
import com.morgandev.kioskacademy.databinding.FragmentRecyclerViewWarriorsBinding
import com.morgandev.kioskacademy.presentation.recyclerViewFragment.RecyclerViewWarriorsViewModel
import com.morgandev.kioskacademy.presentation.recyclerViewFragment.recyclerViewData.RecyclerViewWarriorsAdapter




class DetailedScreenFragment : Fragment() {

    private val detailedScreenFragmentViewModel: RecyclerViewWarriorsViewModel by activityViewModels()

    //new adapter for new recyclerview
    //private lateinit var recyclerViewWarriorsAdapter: RecyclerViewWarriorsAdapter

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

        val number = args.warriorId
        Toast.makeText(context,"$number", Toast.LENGTH_LONG).show()
    }
}