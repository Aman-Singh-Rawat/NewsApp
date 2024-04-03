package com.newsapp.presenter.screen.article

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.core.base.BaseFragment
import com.newsapp.databinding.FragmentStoryPublishedBinding

class ArticlePublishedFragment : BaseFragment() {
    private lateinit var binding: FragmentStoryPublishedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoryPublishedBinding.inflate(
            inflater, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.includePublished.btnOnboardingSkip.setOnClickListener {
            findNavController()
                .navigate(R.id.navigation_profile)
        }
        binding.includePublished.btnOnboardingContinue.setOnClickListener {
            findNavController()
//                .navigate(R.id.navigation_profile)
        }
        btnTextChange()
    }

    @SuppressLint("SetTextI18n")
    private fun btnTextChange() {
        binding.includePublished.btnOnboardingSkip.text = "Back to Home"
        binding.includePublished.btnOnboardingContinue.text = "View Story"
    }


}