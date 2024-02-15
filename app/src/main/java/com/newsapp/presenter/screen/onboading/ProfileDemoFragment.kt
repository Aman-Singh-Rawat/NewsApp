package com.newsapp.presenter.screen.onboading

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.databinding.FragmentProfileBinding
import com.newsapp.databinding.FragmentProfileDemoBinding

class ProfileDemoFragment : Fragment() {
    private lateinit var binding: FragmentProfileDemoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileDemoBinding.inflate(
            inflater, container, false
        )

        binding.btnProfileContinue.setOnClickListener {
            openElevateActivity()
        }

        binding.btnProfileSkip.setOnClickListener {
            openWelcomeActivity()
        }

        return binding.root
    }

    private val openElevateActivity = {
        findNavController().navigate(R.id.action_profileFragment_to_elevateFragment)
    }
    private val openWelcomeActivity = {
        findNavController().navigate(R.id.action_profileFragment_to_WelcomeFragment)
    }
}