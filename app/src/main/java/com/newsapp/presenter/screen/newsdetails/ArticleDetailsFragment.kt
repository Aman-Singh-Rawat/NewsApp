package com.newsapp.presenter.screen.newsdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.newsapp.R
import com.newsapp.core.base.BaseFragment
import com.newsapp.databinding.FragmentArticleDetailsBinding
import com.newsapp.presenter.viewmodel.ArticleDetailViewModel
import com.newsapp.util.glideImage

class ArticleDetailsFragment: BaseFragment() {
    private lateinit var binding: FragmentArticleDetailsBinding
    private val newsAdapter: NewsAdapter = NewsAdapter()
    private val commentAdapter: CommentAdapter = CommentAdapter()
    private val viewModel by activityViewModels<ArticleDetailViewModel>()
    private val articleId by lazy {arguments?.getString("articleId") ?: ""}
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

        binding.tvCommentViewAll.setOnClickListener {
            findNavController().navigate(R.id.commentFragment)
        }
        binding.ivbackArrow.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.ivBookMark.setOnClickListener {
            findNavController().navigate(R.id.bookMarkBottomSheetFragment)
        }

        firebaseSetup()
        rvCommentSetup()
        binding.rvNewsTags.setHasFixedSize(true)
        binding.rvNewsTags.adapter = newsAdapter
        binding.rvNewsTags.layoutManager =
            StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL)

    }

    private fun firebaseSetup() {
        showProgress()
        viewModel.getArticleData(articleId) {article ->
            glideImage(binding.fullImg, article.image)
            glideImage(binding.imgChannelLogo, article.image)
            binding.tvFullHead.text = article.title
            binding.tvNewsDesc.text = article.story
            binding.tvMinuteRead.text = calculateElapsedTime(article.time)

            binding.rvNewsTags.adapter = TagsAdapter(article.tags)

        }
        hideProgress()
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

    private fun rvCommentSetup() {
        binding.rvComment.layoutManager =
            LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL,
                false
            )
        binding.rvComment.adapter = commentAdapter
        commentAdapter.updateUi(getComment())
    }

    private fun getdata(): List<String> {
        return listOf(
            "politics", "corruption", "investiagtion", "crime", "government", "report", "cnn"
        )
    }

    private fun getComment(): List<CommentData> {
        return listOf(
            CommentData(
                R.drawable.img_girl_profile,
                "Sanjuanita Ordonez",
                "3 day ago",
                "This investigative report is a powerful reminder of the importance of transparency and accountability in our political system.",
                "256"
            ),
            CommentData(
                R.drawable.imp_person_one,
                "Sanju Ordonez",
                "2 day ago",
                "This investigative report is a powerful reminder of the importance of transparency and accountability in our political system.",
                "36"
            ),
            CommentData(
                R.drawable.img_girl_profile,
                "anita Ordonez",
                "1 day ago",
                "This investigative report is a powerful reminder of the importance of transparency and accountability in our political system.",
                "144"
            )

        )


    }
}
