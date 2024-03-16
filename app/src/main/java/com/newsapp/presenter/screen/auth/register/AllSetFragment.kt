package com.newsapp.presenter.screen.auth.register

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.newsapp.MainActivity
import com.newsapp.R
import com.newsapp.databinding.FragmentAllSetBinding

class AllSetFragment : Fragment() {
    private lateinit var binding: FragmentAllSetBinding
    private var letsGo: Button? = null
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
        binding.includeAllSet.btnAllInOne.setOnClickListener {
            openActivityMain()
        }
        letsGo = binding.includeAllSet.root.findViewById(R.id.btnAllInOne)
        letsGo?.text = "Let's Go"
        return binding.root
    }
    private fun openActivityMain() {
        val intent = Intent(
            requireContext(),
            MainActivity::class.java
        )
        startActivity(intent)
        requireActivity().finish()

    }
    private val onBackPressed = {
        findNavController()
            .navigateUp()
        true
    }
}