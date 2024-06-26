package com.newsapp.presenter.screen.article

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.newsapp.R
import com.newsapp.data.models.Article
import com.newsapp.databinding.FragmentPreviewStoryBinding
import com.newsapp.presenter.viewmodel.CreateArticleViewModel
import com.newsapp.util.glideImage

class PreviewArticleFragment : Fragment() {
    private lateinit var binding: FragmentPreviewStoryBinding
    private val viewModel by activityViewModels<CreateArticleViewModel>()
    private val isSecond by lazy { arguments?.getBoolean("isSecond") ?: false }
    private val articleId by lazy { arguments?.getString("articleId") ?: "" }
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
            findNavController().navigate(R.id.fragmentPublish,
                bundleOf("articleId" to articleId, "isSecond" to isSecond))
        }
    }
    private fun setData() {
        binding.run {
            viewModel.getArticle()?.let { article ->
                if (articleId.isEmpty() || isSecond)
                    imgPreview.setImageURI(viewModel.imageUri)
                else
                    glideImage(requireActivity(), imgPreview, article.image)

                tvTitle.text = article.title
                binding.tvStory.text = Html.fromHtml(article.story, Html.FROM_HTML_MODE_LEGACY)
            }
        }
    }
}