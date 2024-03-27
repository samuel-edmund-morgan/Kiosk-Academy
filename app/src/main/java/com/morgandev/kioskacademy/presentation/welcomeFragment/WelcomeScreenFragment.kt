package com.morgandev.kioskacademy.presentation.welcomeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.morgandev.kioskacademy.R
import com.morgandev.kioskacademy.databinding.ActivityMainBinding
import com.morgandev.kioskacademy.databinding.FragmentWelcomeScreenBinding

class WelcomeScreenFragment : Fragment() {

    private var _binding: FragmentWelcomeScreenBinding? = null
    private val binding: FragmentWelcomeScreenBinding
        get() = _binding ?:  throw RuntimeException("FragmentWelcomeScreenBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentWelcomeScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Setup On Click Listeners
        setupOnClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupOnClickListeners(){
        with(binding) {
//            yearImage.setOnClickListener {
//                launchRecyclerViewWarriorsFragment()
//            }
            mainMessage.setOnClickListener {
                launchRecyclerViewWarriorsFragment()
            }
            ssuEmblem.setOnClickListener {
                launchRecyclerViewWarriorsFragment()
            }
            bulletHole.setOnClickListener {
                launchRecyclerViewWarriorsFragment()
            }
            root.setOnClickListener {
                launchRecyclerViewWarriorsFragment()
            }
        }
    }

    private fun launchRecyclerViewWarriorsFragment(){
        findNavController().navigate(R.id.action_welcomeScreenFragment_to_recyclerViewWarriorsFragment)
    }

    companion object {
        fun newInstance() = WelcomeScreenFragment()
    }

}