package com.newsapp.presenter.screen.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.databinding.FragmentStoryPublishedBinding

class StoryPublishedFragment : Fragment() {
    private lateinit var binding: FragmentStoryPublishedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoryPublishedBinding.inflate(
            inflater, container, false
        )
        binding.imgStoryBackArrow.setOnClickListener {
            onBackPressed()
        }
        binding.includePublished.btnOnboardingSkip.setOnClickListener {
            onBackPressed()
        }
        btnTextChange()
        return binding.root
    }
    val onBackPressed = {
        findNavController()
            .navigateUp()
        true
    }
    private fun btnTextChange() {
        binding.includePublished.btnOnboardingSkip.text = "Back to Home"
        binding.includePublished.btnOnboardingContinue.text = "View Story"
    }


}