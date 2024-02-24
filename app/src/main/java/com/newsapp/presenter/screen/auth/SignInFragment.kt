package com.newsapp.presenter.screen.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private var btnSignUP: Button? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        btnSignUP = binding.btnSignUP.root.findViewById(R.id.btnAllInOne)

        btnSignUP?.setOnClickListener {
            signInDialogFragment()
        }

        binding.ivBackArrowSignIn.setOnClickListener {
            onBackPressed()
        }
        return binding.root
    }

    val signInDialogFragment = {
        findNavController().navigate(R.id.signInDialogFragment)
    }
    private fun onBackPressed(): Boolean {
        findNavController().navigateUp()
        return true
    }














}


