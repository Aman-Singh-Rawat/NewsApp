package com.newsapp.presenter.screen.auth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.databinding.FragmentSignInBinding
import com.newsapp.presenter.screen.onboading.WelcomeActivity
import com.newsapp.presenter.screen.onboading.WelcomeFragment
class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        binding.btnSignIn.root.setOnClickListener {
            signInDialogFragment()
        }

        binding.ivArrow.setOnClickListener {
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


