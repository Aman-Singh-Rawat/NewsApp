package com.newsapp.presenter.screen.newsdetails

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.util.Log
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
import com.newsapp.util.SharedPrefsManager
import com.newsapp.util.calculateElapsedTime
import com.newsapp.util.glideImage

class ArticleDetailsFragment: BaseFragment() {
    private lateinit var binding: FragmentArticleDetailsBinding
    private val newsAdapter: NewsAdapter = NewsAdapter()
    private val commentAdapter: CommentAdapter = CommentAdapter()
    private val viewModel by activityViewModels<ArticleDetailViewModel>()
    private val articleId by lazy {arguments?.getString("articleId") ?: ""}
    private val prefs by lazy { SharedPrefsManager.getInstance(requireActivity()) }

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
        binding.btnFollow.setOnClickListener {
            followButton()
        }

        viewModel.getFollowing {
            doFollowOrNot(it)
        }

        firebaseSetup()
        rvCommentSetup()
        binding.rvNewsTags.setHasFixedSize(true)
        binding.rvNewsTags.adapter = newsAdapter
        binding.rvNewsTags.layoutManager =
            StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL)

    }

    private fun doFollowOrNot(it: Boolean) {
        if (it) {
            binding.btnFollow.text = resources.getString(R.string.following)
            binding.btnFollow.setBackgroundColor(Color.BLACK)
            binding.btnFollow.setTextColor(Color.WHITE)
        } else {
            binding.btnFollow.text = resources.getString(R.string.follow)
            binding.btnFollow.setBackgroundColor(Color.WHITE)
            binding.btnFollow.setTextColor(Color.BLACK)
        }
    }

    private fun followButton() {
        if (binding.btnFollow.text.toString() == resources.getString(R.string.follow)) {
            viewModel.followedOrNot(true) {
                doFollowOrNot(it)
            }
        } else {
            Log.d("working", "followBtnElse")
            viewModel.followedOrNot(false) {
                doFollowOrNot(it)
            }
        }
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
            binding.tvChannelDesc.text = article.authorDescription
            binding.tvNewsDesc.text = Html.fromHtml(article.story, Html.FROM_HTML_MODE_LEGACY)
            binding.tvDaysAgo.text = calculateElapsedTime(article.time)
            binding.rvNewsTags.adapter = TagsAdapter(article.tags)
            binding.tvCommentTime.text = "${article.comments} comments"

            prefs.getUser()?.let {
                if (article.authorId == it.uid) {
                    binding.btnFollow.visibility = View.GONE
                }
            }
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
}
