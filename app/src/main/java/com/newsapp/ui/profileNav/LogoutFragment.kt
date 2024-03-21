package com.newsapp.ui.profileNav

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.newsapp.R
import com.newsapp.databinding.FragmentLogoutBinding
import com.newsapp.presenter.screen.auth.login.LoginViewModel
import com.newsapp.util.PrefKeys
import com.newsapp.util.SharedPrefsManager

class LogoutFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentLogoutBinding
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
        prefs.logout()

        val navController = findNavController()

        // Get the ID of the start destination
        val startDestinationId = navController.graph.startDestinationId

        // Clear the entire back stack up to the start destination
        navController.popBackStack(startDestinationId, false)

        /* Google logout part */
        val loginViewModel = LoginViewModel(requireActivity().application)
        loginViewModel.logout()

        // Navigate to the sign-in Fragment
        navController.navigate(R.id.signInFragment2)
    }

}