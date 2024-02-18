package com.newsapp.presenter.screen.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        binding.btnWelcome.setOnClickListener {
            signInDialogFragment()
        }

        binding.ivArrowWelcomeBack.setOnClickListener {
            onBackPressed()
        }
        return binding.root
    }

    val signInDialogFragment = {
        findNavController().navigate(R.id.action_signInFragment_to_signInDialogFragment)
    }
    private fun onBackPressed(): Boolean {
        findNavController().navigateUp()
        return true
    }














}


