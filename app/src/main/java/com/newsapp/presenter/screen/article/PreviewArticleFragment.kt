package com.newsapp.presenter.screen.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.databinding.FragmentPreviewStoryBinding

class PreviewArticleFragment : Fragment() {
    private lateinit var binding: FragmentPreviewStoryBinding
    private val viewModel :  CreateArticleViewModel by viewModels()

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
            viewModel.addArticle(
                "",
                title = binding.tvTitle.toString(),
                story = binding.tvStory.toString()
            )
            findNavController().navigate(R.id.fragmentPublish)
        }
    }
    private fun setData() {
        val article = viewModel.getArticle()
        binding.tvTitle.setText(article.title)
        binding.tvStory.setText( article.story)
    }


}