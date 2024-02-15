package com.newsapp.presenter.screen.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.databinding.FragmentAllSetBinding

class AllSetFragment : Fragment() {
    private lateinit var binding: FragmentAllSetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllSetBinding.inflate(
            inflater, container, false
        )
        binding.ivBackArrowSet.setOnClickListener {
            onBackPressed()
        }
        return binding.root
    }
    private val onBackPressed = {
        findNavController()
            .navigateUp()
        true
    }
}