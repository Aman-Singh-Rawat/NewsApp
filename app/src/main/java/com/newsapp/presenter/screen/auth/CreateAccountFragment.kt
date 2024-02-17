package com.newsapp.presenter.screen.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.databinding.FragmentCreateAccountBinding

class CreateAccountFragment : Fragment() {
    private lateinit var binding: FragmentCreateAccountBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateAccountBinding
            .inflate(inflater, container, false)

        binding.btn.setOnClickListener {
            openNewsFeed()
        }
        binding.ivBackArrowCreate.setOnClickListener { // this function work on back button
            onBackPressed()
        }
        return binding.root
    }

    private fun openNewsFeed() {
        findNavController().navigate(R.id.action_signUpFragment_to_NewsFeedFragment)
    }
    private val onBackPressed = { // previous activity navigate
        findNavController().navigateUp()
        true
    }

}