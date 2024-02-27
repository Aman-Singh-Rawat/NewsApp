package com.newsapp.presenter.screen.auth

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    private lateinit var binding: FragmentWelcomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(
            inflater, container, false
        )
        binding.btnWelSignInWith.btnAllInOne.setOnClickListener {
            openSignInFragment()
        }
        binding.includeWelSignUp.tvSgnIn.setOnClickListener {
            openCreateAccountFragment()
        }
        changeText()
        return binding.root
    }
    private fun changeText() {
        binding.btnWelSignInWith.btnAllInOne.text = "Sign in with password"

    }
    private fun openCreateAccountFragment() {
        findNavController().navigate(R.id.createAccountFragment)
    }
    private fun openSignInFragment() {
        findNavController().navigate(R.id.signInFragment)
    }

}