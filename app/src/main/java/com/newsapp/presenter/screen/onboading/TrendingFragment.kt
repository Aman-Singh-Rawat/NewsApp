package com.newsapp.presenter.screen.onboading

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.databinding.FragmentTrendingDemoBinding

class TrendingFragment : Fragment() {
    private lateinit var binding: FragmentTrendingDemoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrendingDemoBinding.inflate(
            inflater, container, false
        )

        binding.btnTrendingContinue.setOnClickListener {
            openProfileDemo()
        }

        binding.btnTrendingSkip.setOnClickListener {
            openWelcomeActivity()
        }
        return binding.root
    }

    private val openWelcomeActivity = {
        findNavController().navigate(R.id.action_trendingFragment_to_WelcomeFragment)
    }

    private val openProfileDemo = {
        findNavController().navigate(R.id.action_trendingFragment_to_profileFragment)
    }

}