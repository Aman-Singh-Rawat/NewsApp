package com.newsapp.presenter.screen.newsdetails

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.newsapp.R
import com.newsapp.core.base.BaseFragment
import com.newsapp.databinding.FragmentArticleDetailsBinding
import com.newsapp.presenter.viewmodel.ArticleDetailViewModel
import com.newsapp.util.calculateElapsedTime
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
            findNavController().navigate(R.id.commentFragment, bundleOf("articleId" to articleId))
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
        viewModel.getArticleData(articleId) {article ->
            viewModel.articleVisited(article)
            binding.nsvRoot.visibility = View.VISIBLE
            binding.progress.visibility = View.GONE
            binding.tvTotalViews.text = article.userViewed.size.toString()
            glideImage(binding.fullImg, article.image)
            glideImage(binding.imgChannelLogo, article.authorProfile)
            glideImage(binding.imgLogo, article.authorProfile)
            binding.tvName.text = article.authorName
            binding.tvFullHead.text = article.title
            binding.tvChannelName.text = article.authorName
            binding.tvNewsDesc.text = Html.fromHtml(article.story, Html.FROM_HTML_MODE_LEGACY)
            binding.tvDaysAgo.text = calculateElapsedTime(article.time)
            binding.rvNewsTags.adapter = TagsAdapter(article.tags)
            binding.tvCommentTime.text = "${article.comments} comments"
        }

    }



    private fun rvCommentSetup() {
        binding.rvComment.layoutManager =
            LinearLayoutManager(
                requireActivity(), LinearLayoutManager.VERTICAL,
                false
            )
        binding.rvComment.adapter = commentAdapter
        //commentAdapter.updateUi(getComment())
    }

   /* private fun getComment(): List<Comment> {
        return listOf(
            Comment(
                R.drawable.img_girl_profile,
                "Sanjuanita Ordonez",
                "3 day ago",
                "This investigative report is a powerful reminder of the importance of transparency and accountability in our political system.",
                "256"
            ),
            Comment(
                R.drawable.imp_person_one,
                "Sanju Ordonez",
                "2 day ago",
                "This investigative report is a powerful reminder of the importance of transparency and accountability in our political system.",
                "36"
            ),
            Comment(
                R.drawable.img_girl_profile,
                "anita Ordonez",
                "1 day ago",
                "This investigative report is a powerful reminder of the importance of transparency and accountability in our political system.",
                "144"
            )

        )


    }*/
}
