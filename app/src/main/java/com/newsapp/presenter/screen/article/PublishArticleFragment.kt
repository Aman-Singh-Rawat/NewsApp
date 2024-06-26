package com.newsapp.presenter.screen.article

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.newsapp.R
import com.newsapp.core.base.BaseFragment
import com.newsapp.data.models.Article
import com.newsapp.databinding.FragmentPublishBinding
import com.newsapp.presenter.viewmodel.CreateArticleViewModel
import com.newsapp.util.glideImage
import com.newsapp.util.hideKeyboard

class PublishArticleFragment : BaseFragment(), OnItemSelectedListener {
    private lateinit var binding: FragmentPublishBinding
    private val articleId by lazy { arguments?.getString("articleId") ?: "" }
    private val isSecond by lazy { arguments?.getBoolean("isSecond") ?: false }
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

        binding.spinnerPublish.onItemSelectedListener = this

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
            if (!mTopic.isNullOrEmpty() && mTopic != "Select" && articleId.isEmpty()) {
                showProgress()
                viewModel.uploadImageToFirebase(isSecond, onSuccess = {
                    viewModel.publishArticle(mTopic!!, tags)
                    hideProgress()
                    findNavController().navigate(R.id.fragmentStoryPublished)
                    viewModel.clearArticleData()
                })
            } else if (!mTopic.isNullOrEmpty() && mTopic != "Select" && articleId.isNotEmpty()) {
                showProgress()
                viewModel.uploadImageToFirebase(isSecond) {
                    viewModel.updateArticle(mTopic!!, tags, articleId, {
                        viewModel.clearArticleData()
                        hideProgress()
                        Toast.makeText(requireActivity(), "Successfully updated", Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.fragmentStoryPublished)
                    }) {
                        hideProgress()
                    }
                }
            } else {
                Toast.makeText(requireActivity(), "please enter the all values", Toast.LENGTH_SHORT).show()
            }
        }
        binding.imgPublishBack.setOnClickListener {
            findNavController()
                .navigateUp()
        }
    }

    private fun spinnerFunctionality() {
        val arrayAdapter = ArrayAdapter.createFromResource(
            requireActivity(), R.array.stringSelectTopic,
            android.R.layout.simple_spinner_item
        )
        arrayAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        binding.spinnerPublish.adapter = arrayAdapter
    }

    private fun addChips(text: String) {
        val chip = Chip(requireActivity())
        chip.text = text
        chip.isCloseIconVisible = true
        chip.setOnCloseIconClickListener {
            binding.chipGroup.removeView(chip)
        }
        binding.chipGroup.addView(chip)
        tags.add(chip.text.toString())
        chip.setOnCloseIconClickListener{
            binding.chipGroup.removeView(chip)
            tags.remove(chip.text)
        }
    }

    private fun setData() {

        viewModel.getArticle()?.let { article ->
            binding.tvTheRise.text = article.title
            if (articleId.isEmpty() || isSecond)
                binding.imgPublish.setImageURI(viewModel.imageUri)
            else
                glideImage(requireActivity(), binding.imgPublish, article.image)
        }

    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        mTopic = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}