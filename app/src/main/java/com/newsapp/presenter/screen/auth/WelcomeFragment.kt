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
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    lateinit var binding: FragmentWelcomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(
            inflater, container, false
        )

        textColorChange() // Change the color of "Don't have an account?  Sign up"


        binding.btnWelSignInWith.setOnClickListener {
            openSignInFragment()
        }
        return binding.root
    }
    private val textColorChange = {
        val spannableString = SpannableString("Don't have an account?  Sign up")
        val signUpColor = ContextCompat.getColor(
            requireContext(), R.color.blue
        )
        val startIndex = spannableString.indexOf("Sign up")
        spannableString.setSpan(
            ForegroundColorSpan(signUpColor),
            startIndex, startIndex + "Sign up".length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvWelNotAccount.text = spannableString
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvWelNotAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }
    private val openSignInFragment = {
        findNavController().navigate(R.id.action_welcomeFragment_to_signInFragment)
    }

}