package com.newsapp.presenter.screen.newsdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.newsapp.core.base.BaseFragment
import com.newsapp.databinding.FragmentArticleDetailsBinding
import com.newsapp.presenter.viewmodel.ArticleDetailViewModel
import com.newsapp.util.glideImage

class ArticleDetailsFragment: BaseFragment() {
    private lateinit var binding: FragmentArticleDetailsBinding
    private val viewModel by activityViewModels<ArticleDetailViewModel>()
    private val articleId by lazy {arguments?.getString("articleId") ?: ""}
    private val position by lazy {arguments?.getInt("position") ?: 0}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleDetailsBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseSetup()
        binding.rvNewsTags.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
    }

    private fun firebaseSetup() {
        viewModel.getArticleData(articleId) {article ->
            glideImage(binding.fullImg, article.image)
            glideImage(binding.imgChannelLogo, article.image)
            binding.tvFullHead.text = article.title
            binding.tvNewsDesc.text = article.story
            binding.tvMinuteRead.text = calculateElapsedTime(article.time)

            binding.rvNewsTags.adapter = TagsAdapter(article.tags)

        }
    }
    private fun calculateElapsedTime(timestamp: Long): String {
        val currentTime = System.currentTimeMillis()
        val elapsedTimeMillis = currentTime - timestamp

        val seconds = elapsedTimeMillis / 1000
        val minutes = seconds / 60
        val hours = minutes / 60

        return when {
            hours > 0 -> "$hours hours ago"
            minutes > 0 -> "$minutes minutes ago"
            else -> "$seconds seconds ago"
        }
    }

}