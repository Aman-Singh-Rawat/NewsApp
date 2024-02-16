package com.newsapp.presenter.screen.profile.publish

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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

        spinnerFunctionality()
        recyclerViewFunctionality()
        return binding.root
    }

     private fun list(): List<String> {
         return listOf<String>("Technology", "ai", "computer", "artificialIntelligence",
             "innovation", "machine", "digital", "robot")
    }

    private fun recyclerViewFunctionality() {
        val tagsRecycler = TagsRecycler(list())

        binding.recyclerPublish.layoutManager = GridLayoutManager(requireContext(),
            2)
        binding.recyclerPublish.adapter = tagsRecycler
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

}