package com.newsapp.presenter.screen.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.newsapp.R
import com.newsapp.databinding.FragmentForgetPasswordBinding
import com.newsapp.presenter.viewmodel.LoginViewModel

class ForgetPassword : Fragment() {
    private lateinit var binding: FragmentForgetPasswordBinding
    private val viewModel : LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgetPasswordBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp()

    }

    private fun setUp() {
        binding.includeButton.btnAllInOne.apply {
            text = resources.getString(R.string._continue)
            setOnClickListener {
                if (binding.etFillEmail.text?.isNotEmpty() == true) {
                    viewModel.resetPassword(binding.etFillEmail.text.toString(), {
                        findNavController().navigate(R.id.signInDialogFragment, bundleOf("forgetPassword" to true))
                    }, {

                    })
                }
            }
        }
    }
}