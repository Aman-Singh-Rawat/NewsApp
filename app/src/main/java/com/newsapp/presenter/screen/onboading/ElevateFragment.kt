package com.newsapp.presenter.screen.onboading

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.databinding.FragmentElevateBinding
import com.newsapp.presenter.screen.auth.SignUp

class ElevateFragment : Fragment() {
    lateinit var binding: FragmentElevateBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentElevateBinding.inflate(
            inflater, container, false
        )

        binding.btnElevateContinue.setOnClickListener {
            val intent = Intent(requireContext(),SignUp::class.java)
            startActivity(intent)
        }

        return binding.root
    }

}