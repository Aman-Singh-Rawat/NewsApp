package com.newsapp.presenter.screen.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.databinding.FragmentCreateAccountBinding

class CreateAccountFragment : Fragment() {
    private lateinit var binding: FragmentCreateAccountBinding
    private var btnAllInOne: Button? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateAccountBinding
            .inflate(inflater, container, false)

        btnAllInOne = binding.btnNewFeed.root.findViewById(R.id.btnAllInOne)
        btnAllInOne?.setOnClickListener {
            openNewsFeed()
        }
        binding.ivBackArrowCreate.setOnClickListener { // this function work on back button
            onBackPressed()
        }
        binding.includeCreateSignIn.tvSgnIn.setOnClickListener {
            navigateSignIn()
        }
        tvChange() // This function change the text of SignIn button
        propertyOfInclude()
        return binding.root
    }
    private fun navigateSignIn() {
        findNavController().navigate(R.id.signInFragment)
    }

    private fun tvChange() {
        binding.includeCreateSignIn.tvAlreadyAccount
            .text = "Already have an account?"

        binding.includeCreateSignIn.tvSgnIn
            .text = "Sign in"
    }
    private fun propertyOfInclude() {

    }

    private fun openNewsFeed() {
        findNavController().navigate(R.id.newsFeedFragment)
    }
    private fun onBackPressed() { // previous activity navigate
        findNavController().navigateUp()
    }

}