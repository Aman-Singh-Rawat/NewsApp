package com.newsapp.presenter.screen.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.databinding.FragmentPreviewStoryBinding

class PreviewArticleFragment : Fragment() {
    private lateinit var binding: FragmentPreviewStoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPreviewStoryBinding.inflate(
            inflater, container, false
        )
        binding.ivArrowStory.setOnClickListener { // back button
            onBackPressed()
        }
        binding.tvContinue.setOnClickListener {
            openFragmentPublish()
        }
        return binding.root
    }

    val openFragmentPublish = {
        findNavController().navigate(R.id.fragmentPublish)
    }
    val onBackPressed = {
        findNavController()
            .navigateUp()
        true
    }

}