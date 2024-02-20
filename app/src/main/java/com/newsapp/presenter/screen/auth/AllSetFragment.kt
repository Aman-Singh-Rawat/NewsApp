package com.newsapp.presenter.screen.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
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
        letsGo = binding.includeAllSet.root.findViewById(R.id.btnAllInOne)
        letsGo?.text = "Let's Go"
        return binding.root
    }
    private val onBackPressed = {
        findNavController()
            .navigateUp()
        true
    }
}