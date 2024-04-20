package com.newsapp.presenter.screen.auth.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.databinding.FragmentSignInBinding
import com.newsapp.presenter.screen.auth.register.SignUpActivity
import com.newsapp.presenter.viewmodel.LoginViewModel

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private var isPasswordVisible = false
    private val viewModel : LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)


// Set an OnTouchListener for the EditText

        return binding.root
    }

    private fun passVisibleOrNot(etPassword: EditText) {
        etPassword.transformationMethod = PasswordTransformationMethod()

// Set an OnTouchListener for the EditText
        etPassword.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (etPassword.right - etPassword.compoundPaddingRight)) {
                    isPasswordVisible = !isPasswordVisible

                    // Update the transformation method and drawable based on the current visibility state
                    if (isPasswordVisible) {
                        etPassword.transformationMethod = null // Show password as plain text
                        etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireActivity(), R.drawable.img_visible_eye), null)
                    } else {
                        etPassword.transformationMethod = PasswordTransformationMethod() // Show password as dots
                        etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireActivity(), R.drawable.img_invisible_eye), null)
                    }
                    etPassword.setSelection(etPassword.length()) // Move the cursor to the end
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvForgetPass.setOnClickListener {
            findNavController().navigate(R.id.forgetPassword)
        }

        val etPassword = binding.includeFragSignIn.etFillPassWord

        val drawable = ContextCompat.getDrawable(requireActivity(), R.drawable.img_invisible_eye)
        etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
        passVisibleOrNot(etPassword)

        setupUI()
    }

    @SuppressLint("SetTextI18n")
    private fun setupUI() {
        binding.btnSignUP.btnAllInOne.setOnClickListener {
            val email = binding.includeFragSignIn.etFillEmail.text.toString()
            val password = binding.includeFragSignIn.etFillPassWord.text.toString()
            viewModel.login(email,password, onSuccess = {
                findNavController().navigate(R.id.signInDialogFragment)
            }, onError = {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            })
        }

        binding.ivBackArrowSignIn.setOnClickListener {
            startActivity(Intent(requireActivity(),SignUpActivity::class.java))
        }

        binding.includeSignUp.tvSgnIn.setOnClickListener {
            findNavController().navigate(R.id.createAccountFragment)
        }

        binding.btnSignUP.btnAllInOne.text = "Sign in"
    }

}


