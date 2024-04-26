package com.newsapp.presenter.screen.auth.register

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.newsapp.MainActivity
import com.newsapp.R
import com.newsapp.databinding.FragmentSignInDialogBinding

@Suppress("UNREACHABLE_CODE")
class SignInDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentSignInDialogBinding
    private val isForgetPassword by lazy { arguments?.getBoolean("forgetPassword") ?: false }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireActivity(), android.R.color.transparent))
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isForgetPassword) {
            binding.tvSignInSucc.text = "Reset Password\nSuccessful!"
            binding.tvHomePage.text = "Please checkout your Email"
            navigateToSignInFragment()
        } else {
            openMainActivity()
        }

        dialog?.setCancelable(false)
    }

    private fun navigateToSignInFragment() {
        Handler(Looper.getMainLooper()).postDelayed({
            // Check if the fragment is still added before navigating
            if (isAdded) {
                findNavController().navigate(
                    R.id.signInFragment,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.signInFragment, true)
                        .build()
                )
            }
        }, 2000)
    }

    private fun openMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            // Check if the fragment is still added before starting the activity
            if (isAdded) {
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }, 2000)
    }
}