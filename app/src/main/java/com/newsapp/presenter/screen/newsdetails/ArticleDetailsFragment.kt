package com.newsapp.presenter.screen.newsdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.newsapp.core.base.BaseFragment
import com.newsapp.data.models.Article
import com.newsapp.databinding.FragmentArticleDetailsBinding
import com.newsapp.presenter.viewmodel.ArticleDetailViewModel

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
    }

    private fun firebaseSetup() {
        viewModel.getArticleData(articleId) {
            glideImage(it[position].image, binding.fullImg)
            glideImage(it[position].image, binding.imgChannelLogo)
            binding.tvFullHead.text = it[position].title
            binding.tvNewsDesc.text = it[position].story
            binding.tvMinuteRead.text = calculateElapsedTime(it[position].time)
        }
    }
    private fun glideImage(imageUrl: String, imageView: ImageView) {
        Glide.with(requireContext())
            .load(imageUrl)
            .into(imageView)
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