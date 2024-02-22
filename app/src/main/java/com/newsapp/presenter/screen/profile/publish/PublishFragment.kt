package com.newsapp.presenter.screen.profile.publish

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.chip.Chip
import com.newsapp.R
import com.newsapp.databinding.FragmentPublishBinding

class PublishFragment : Fragment() {
    private lateinit var binding: FragmentPublishBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPublishBinding.inflate(
            inflater, container, false
        )
        binding.etAddTags.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (binding.etAddTags.text.isNotBlank()) {
                    addChips(binding.etAddTags.text.toString())
                    binding.etAddTags.setText("")
                }
                return@setOnEditorActionListener true
            }
            false
        }
        //Todo

        spinnerFunctionality()
        //recyclerViewFunctionality()

        binding.tvPublish.setOnClickListener {
            openStoryPublished()
        }
        binding.imgPublishBack.setOnClickListener {
            onBackPressed()
        }

        return binding.root
    }

     private fun list(): List<String> {
         return listOf<String>("Technology", "ai", "computer", "artificialIntelligence",
             "innovation", "machine", "digital", "robot")
    }
    private fun spinnerFunctionality() {
        val arrayAdapter = ArrayAdapter.createFromResource(
            requireContext(), R.array.stringSelectTopic,
            android.R.layout.simple_spinner_item
        )
        arrayAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        binding.spinnerPublish.adapter = arrayAdapter
    }

    val openStoryPublished = {
        findNavController().navigate(
            R.id.action_fragmentPublish_to_fragmentStoryPublished)
    }

    val onBackPressed = {
        findNavController()
            .navigateUp()
        true
    }

    private fun addChips(text: String) {
        val chip = Chip(requireContext())
        chip.text = text

        chip.isCloseIconVisible = true
        chip.setOnCloseIconClickListener {
            binding.chipGroup.removeView(chip)
        }
        binding.chipGroup.addView(chip)
    }
}