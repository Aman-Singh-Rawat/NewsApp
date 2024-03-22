package com.newsapp.ui.profileNav

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.newsapp.R
import com.newsapp.databinding.FragmentLogoutBinding
import com.newsapp.presenter.screen.auth.login.LoginViewModel
import com.newsapp.presenter.screen.auth.register.SignUp
import com.newsapp.ui.profileNav.viewmodel.ViewModelProfile
import com.newsapp.util.PrefKeys
import com.newsapp.util.PrefKeys.IS_LOGGED_IN
import com.newsapp.util.SharedPrefsManager

class LogoutFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentLogoutBinding
    private val loginViewModel : LoginViewModel by viewModels()
    private val prefs by lazy { SharedPrefsManager.getInstance(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogoutBinding
            .inflate(inflater, container, false
            )
        binding.includeTwoButton.btnOnboardingContinue.setOnClickListener {
            openSignInFragment()
        }
        binding.includeTwoButton.btnOnboardingSkip.setOnClickListener {
            findNavController().navigateUp()
        }
        buttonTextChange()
        return binding.root
    }
    @SuppressLint("SetTextI18n")
    private fun buttonTextChange() {
        binding.includeTwoButton.btnOnboardingSkip.text = "Cancel"
        binding.includeTwoButton.btnOnboardingContinue.text = "Yes, Logout"

    }
    private fun openSignInFragment() {

        prefs.putBoolean(IS_LOGGED_IN, false)
        startActivity(Intent(requireContext(),SignUp::class.java))
        loginViewModel.logout()
        requireActivity().finish()
    }
}