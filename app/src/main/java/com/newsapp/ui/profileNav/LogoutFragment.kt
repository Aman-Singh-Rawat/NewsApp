package com.newsapp.ui.profileNav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.newsapp.R
import com.newsapp.databinding.FragmentLogoutBinding

    class LogoutFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentLogoutBinding
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
    private fun buttonTextChange() {
        binding.includeTwoButton.btnOnboardingSkip.text = "Cancel"
        binding.includeTwoButton.btnOnboardingContinue.text = "Yes, Logout"

    }
    private fun openSignInFragment() {
        findNavController().navigate(R.id.signInFragment2)
    }

}