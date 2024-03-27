package com.newsapp.presenter.screen.auth.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.databinding.FragmentCreateAccountBinding
import com.newsapp.presenter.viewmodel.RegisterViewModel

class CreateAccountFragment : Fragment() {

    private lateinit var binding: FragmentCreateAccountBinding
    private val viewModel: RegisterViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateAccountBinding
            .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()

    }


    private fun setupUI() {
        binding.btnNewFeed.btnAllInOne.setOnClickListener {
            val email = binding.includeFragAccount.etFillEmail.text.toString()
            val password = binding.includeFragAccount.etFillPassWord.text.toString()
            viewModel.register(email, password, onSuccess = {
                findNavController().navigate(R.id.newsFeedFragment)
            }, onError = {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            })
        }
        binding.ivBackArrowCreate.setOnClickListener { // this function work on back button
            onBackPressed()
        }
        binding.includeCreateSignIn.tvSgnIn.setOnClickListener {
            navigateSignIn()
        }
        tvChange() // This function change the text of SignIn button

    }

    private fun navigateSignIn() {
        findNavController().navigate(R.id.signInFragment)
    }

    @SuppressLint("SetTextI18n")
    private fun tvChange() {
        binding.includeCreateSignIn.tvAlreadyAccount
            .text = "Already have an account?"

        binding.includeCreateSignIn.tvSgnIn
            .text = "Sign in"
    }


    private fun onBackPressed() { // previous activity navigate
        findNavController().navigateUp()
    }

}