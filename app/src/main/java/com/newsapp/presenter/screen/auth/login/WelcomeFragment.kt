package com.newsapp.presenter.screen.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.newsapp.R
import com.newsapp.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding
    private val viewModel:LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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