package com.newsapp.presenter.screen.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.newsapp.R
import com.newsapp.databinding.FragmentPublishBinding
import com.newsapp.presenter.viewmodel.CreateArticleViewModel
import com.newsapp.util.hideKeyboard

class PublishArticleFragment : Fragment(), OnItemSelectedListener {
    private lateinit var binding: FragmentPublishBinding
    private val viewModel by activityViewModels<CreateArticleViewModel>()
    private val tags = mutableListOf<String>()
    private var mTopic: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPublishBinding.inflate(
            inflater, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        setUi()

    }

    private fun setUi() {
        binding.etAddTags.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (!binding.etAddTags.text.isNullOrEmpty() && !binding.etAddTags.text.isNullOrBlank()) {
                    addChips(binding.etAddTags.text.toString())
                    binding.etAddTags.setText("")
                    hideKeyboard(v)
                }
                return@setOnEditorActionListener true
            }
            false
        }

        spinnerFunctionality()
        //recyclerViewFunctionality()

        binding.tvPublish.setOnClickListener {
            if (!tags.isNullOrEmpty() && !mTopic.isNullOrEmpty()) {
                viewModel.publishArticle(mTopic!!, tags)
                findNavController().navigate(R.id.fragmentStoryPublished)
            }
        }
        binding.imgPublishBack.setOnClickListener {
            findNavController()
                .navigateUp()
        }
    }

    private fun list(): List<String> {
        return listOf<String>(
            "Technology", "ai", "computer", "artificialIntelligence",
            "innovation", "machine", "digital", "robot"
        )
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

    private fun addChips(text: String) {
        val chip = Chip(requireContext())
        chip.text = text
        chip.isCloseIconVisible = true
        chip.setOnCloseIconClickListener {
            binding.chipGroup.removeView(chip)
        }
        binding.chipGroup.addView(chip)
        tags.add(chip.toString())
    }


    private fun setData() {

        viewModel.getArticle()?.let { article ->
            binding.tvTheRise.text = article.title
        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        mTopic = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}