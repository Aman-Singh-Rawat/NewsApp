package com.newsapp.presenter.screen.auth.login

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.GoogleAuthProvider
import com.newsapp.R
import com.newsapp.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    private lateinit var binding: FragmentWelcomeBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.btnWelSignInWith.btnAllInOne.setOnClickListener {
            openSignInFragment()
        }
        binding.includeWelSignUp.tvSgnIn.setOnClickListener {
            openCreateAccountFragment()
        }
        binding.tvContinueWithGoogle.setOnClickListener {
            viewModel.getGoogleSignInClient()?.signInIntent?.let { launcher.launch(it) }
        }
        changeText()
    }

    @SuppressLint("SetTextI18n")
    private fun changeText() {
        binding.btnWelSignInWith.btnAllInOne.text = "Sign in with password"

    }

    private fun openCreateAccountFragment() {
        findNavController().navigate(R.id.createAccountFragment)
    }

    private fun openSignInFragment() {
        findNavController().navigate(R.id.signInFragment)
    }

    /* Google Authentication */
    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            if (task.isSuccessful) {
                val account: GoogleSignInAccount? = task.result
                val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                viewModel.authenticateGoogleLogin(credential, onSuccessSignIn = {
                    findNavController().navigate(R.id.signInDialogFragment)
                }, onSuccessSignup = {
                    findNavController().navigate(R.id.newsFeedFragment)
                }, onError = {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                })
            }
        } else {
            Toast.makeText(requireContext(), "Failed to signIn from google.", Toast.LENGTH_SHORT).show()
        }
    }
}