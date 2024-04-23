package com.newsapp.presenter.screen.newsdetails

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.print.PrintAttributes.Margins
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.marginEnd
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.chip.Chip
import com.newsapp.R
import com.newsapp.core.base.BaseFragment
import com.newsapp.databinding.FragmentArticleDetailsBinding
import com.newsapp.presenter.viewmodel.ArticleDetailViewModel
import com.newsapp.presenter.viewmodel.BookmarkViewModel
import com.newsapp.presenter.viewmodel.CommentViewModel
import com.newsapp.util.SharedPrefsManager
import com.newsapp.util.calculateElapsedTime
import com.newsapp.util.glideImage

class ArticleDetailsFragment: BaseFragment() {
    private lateinit var binding: FragmentArticleDetailsBinding
    private val newsAdapter: NewsAdapter = NewsAdapter()
    private val commentAdapter: CommentAdapter = CommentAdapter()
    private val commentViewModel by activityViewModels<CommentViewModel> ()
    private val viewModel by activityViewModels<ArticleDetailViewModel>()
    private val bookmarkViewModel by activityViewModels<BookmarkViewModel>()
    private val articleId by lazy {arguments?.getString("articleId") ?: ""}
    private val prefs by lazy { SharedPrefsManager.getInstance(requireActivity()) }
    private var commentSize = 0
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
        binding.btnFollow.setOnClickListener {
            followButton()
        }
        bookmarkViewModel.isArticleSavedOrNot(articleId) {
            if (it) {
                binding.ivBookMark.setImageResource(R.drawable.ic_bookmark_fill)
            } else {
                binding.ivBookMark.setImageResource(R.drawable.ic_bookmark)
            }
        }
        binding.ivBookMark.setOnClickListener {
            bookmarkViewModel.isArticleSavedOrNot(articleId) {
                if (it) {
                    bookmarkViewModel.doArticleSave(articleId, emptyList<String>().toMutableList(), false) {
                        binding.ivBookMark.setImageResource(R.drawable.ic_bookmark)
                    }
                } else {
                    binding.ivBookMark.setOnClickListener {
                        findNavController().navigate(R.id.bookMarkBottomSheetFragment,
                            bundleOf("articleId" to articleId))
                    }
                }
            }
        }

        viewModel.getFollowing(articleId) {
            doFollowOrNot(it)
        }

        firebaseSetup()
        rvCommentSetup()

    }

    private fun doFollowOrNot(it: Boolean) {
        Log.d("debugging", "it is:: $it")
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
            viewModel.followedOrNot(articleId, true) {
                doFollowOrNot(it)
            }
        } else {
            viewModel.followedOrNot(articleId, false) {
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

            addChips(article.tags)

            prefs.getUser()?.let {
                if (article.authorId == it.uid) {
                    binding.btnFollow.visibility = View.GONE
                }
            }
        }

    }

    private fun addChips(list: List<String>) {
        for (data in list) {
            val chip = Chip(requireActivity())
            chip.text = data
            chip.setTextColor(Color.BLACK)
            chip.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.white))
            chip.chipCornerRadius = 40f
            chip.setChipStrokeColorResource(R.color.black)
            chip.chipStrokeWidth = resources.getDimensionPixelSize(R.dimen.chip_stroke_width).toFloat()
            chip.textSize = 16f
            chip.isContextClickable = false
            chip.isCloseIconVisible = false
            binding.rvNewsTags.addView(chip)
        }

    }
    private fun rvCommentSetup() {
        binding.rvComment.layoutManager =
            LinearLayoutManager(
                requireActivity(), LinearLayoutManager.VERTICAL,
                false
            )
        binding.rvComment.adapter = commentAdapter
        commentViewModel.getComments(articleId) {
            binding.tvCommentTime.text = "${it.size} comments"
            binding.tvTotalComments.text = "${it.size} comments"
            viewModel.saveCommentsSize(articleId, it.size)
            if(it.size > 4)
                commentAdapter.updateUi(it.subList(0, 3))
            else
                commentAdapter.updateUi(it)
        }
    }
}
