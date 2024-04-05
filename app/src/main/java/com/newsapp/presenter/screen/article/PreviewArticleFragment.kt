package com.newsapp.presenter.screen.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.newsapp.R
import com.newsapp.data.models.Article
import com.newsapp.databinding.FragmentPreviewStoryBinding
import com.newsapp.presenter.viewmodel.CreateArticleViewModel

class PreviewArticleFragment : Fragment() {
    private lateinit var binding: FragmentPreviewStoryBinding
    private val viewModel by activityViewModels<CreateArticleViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPreviewStoryBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUi()
        setData()
    }

    private fun setUi() {
        binding.ivArrowStory.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.tvContinue.setOnClickListener {
            findNavController().navigate(R.id.fragmentPublish)
        }
    }
    private fun setData() {
        binding.run {
            viewModel.getArticle()?.let { article ->
                binding.imgPreview.setImageURI(viewModel.imageUri)
                tvTitle.text = article.title
                tvStory.text = article.story
            }
        }
    }
}