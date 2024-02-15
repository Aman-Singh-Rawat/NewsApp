package com.newsapp.presenter.screen.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.databinding.FragmentProfileBinding

class PublicProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(
            inflater, container, false
        )
        binding.btnFinish.setOnClickListener {
            openAllSetFragment()
        }
        binding.ivBackArrowProfile.setOnClickListener {
            onBackPressed()
        }
        return binding.root
    }

    val onBackPressed = {
        findNavController().navigateUp()
        true
    }
    val openAllSetFragment = {
        findNavController().navigate(
            R.id.action_public_Profile_Fragment_to_all_Set_Fragment
        )
    }

}