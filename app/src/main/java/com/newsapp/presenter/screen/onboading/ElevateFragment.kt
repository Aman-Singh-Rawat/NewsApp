package com.newsapp.presenter.screen.onboading

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.databinding.FragmentElevateBinding

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

        }

        return binding.root
    }

}