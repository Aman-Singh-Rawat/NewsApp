package com.newsapp.presenter.screen.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding

    private val viewModel : LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.btnSignUP.btnAllInOne.setOnClickListener {
            val email = binding.includeFragSignIn.etFillEmail.text.toString()
            val password = binding.includeFragSignIn.etFillPassWord.text.toString()
            viewModel.login(email,password, onSuccess = {
                findNavController().navigate(R.id.signInDialogFragment)
            }, onError = {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            })
        }
        //dummy

        binding.ivBackArrowSignIn.setOnClickListener {
            onBackPressed()
        }

        binding.includeSignUp.tvSgnIn.setOnClickListener {
            findNavController().navigate(R.id.createAccountFragment)
        }

        binding.btnSignUP.btnAllInOne.text = "Sign in"
    }

    private fun onBackPressed(): Boolean {
        findNavController().navigateUp()
        return true
    }














}


