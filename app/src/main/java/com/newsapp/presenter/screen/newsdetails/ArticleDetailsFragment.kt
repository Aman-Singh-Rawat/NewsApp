package com.newsapp.presenter.screen.newsdetails

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.print.PrintAttributes.Margins
import android.text.Html
import android.text.TextUtils
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
import com.newsapp.presenter.screen.homepage.HpTrendRecycler
import com.newsapp.presenter.screen.profile.ProfileAdapter
import com.newsapp.presenter.viewmodel.ArticleDetailViewModel
import com.newsapp.presenter.viewmodel.BookmarkViewModel
import com.newsapp.presenter.viewmodel.CommentViewModel
import com.newsapp.util.OnItemClickListener
import com.newsapp.util.SharedPrefsManager
import com.newsapp.util.calculateElapsedTime
import com.newsapp.util.glideImage

class ArticleDetailsFragment: BaseFragment(), OnItemClickListener {
    private lateinit var binding: FragmentArticleDetailsBinding
    private val profileAdapter = ProfileAdapter(this)
    private val trendingAdapter = HpTrendRecycler()
    private val commentAdapter: CommentAdapter = CommentAdapter()
    private val commentViewModel by activityViewModels<CommentViewModel> ()
    private val viewModel by activityViewModels<ArticleDetailViewModel>()
    private val bookmarkViewModel by activityViewModels<BookmarkViewModel>()
    private val articleId by lazy {arguments?.getString("articleId") ?: ""}
    private val prefs by lazy { SharedPrefsManager.getInstance(requireActivity()) }
    private var followingString = ""
    private var followString = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleDetailsBinding.inflate(layoutInflater)

        followString = resources.getString(R.string.follow)
        followingString = resources.getString(R.string.following)

        viewModel.getFollowing(articleId) {
            doFollowOrNot(it, followString, followingString)
        }

        return binding.root
    }

    private fun setUpUserRecycler() {
        binding.rvUserRecycler.adapter = trendingAdapter
        viewModel.getAllArticles(articleId) {
            if(it[0].authorId != prefs.getUser()?.uid)
                binding.clTrendingStories.visibility = View.VISIBLE
            else
                trendingAdapter.updateUi(it, requireActivity())
        }
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
        binding.tvViewAll.setOnClickListener {
            findNavController().navigate(R.id.userProfileFragment, bundleOf("articleId" to articleId))
        }

        bookMarkFunctionality()
        setUpUserRecycler()
        firebaseSetup()
        rvCommentSetup()

    }

    private fun bookMarkFunctionality() {
        binding.ivBookMark.setOnClickListener {
            bookmarkViewModel.isArticleSavedOrNot(articleId) {
                if (it) {
                    bookmarkViewModel.doArticleSave(
                        articleId,
                        emptyList<String>().toMutableList(),
                        false
                    ) {
                        binding.ivBookMark.setImageResource(R.drawable.ic_bookmark)
                    }
                } else {
                    binding.ivBookMark.setOnClickListener {
                        findNavController().navigate(
                            R.id.bookMarkBottomSheetFragment,
                            bundleOf("articleId" to articleId)
                        )
                    }
                }
            }
        }
    }

    private fun doFollowOrNot(isFollowing: Boolean, followString: String, followingString: String) {
        if (isFollowing) {
            binding.btnFollow.text = followingString
            binding.btnFollow.setBackgroundColor(Color.BLACK)
            binding.btnFollow.setTextColor(Color.WHITE)
        } else {
            binding.btnFollow.text = followString
            binding.btnFollow.setBackgroundColor(Color.WHITE)
            binding.btnFollow.setTextColor(Color.BLACK)
        }
    }

    private fun followButton() {
        if (binding.btnFollow.text.toString() == resources.getString(R.string.follow)) {
            viewModel.followedOrNot(articleId, true) {
                doFollowOrNot(it, followString, followingString)
            }
        } else {
            viewModel.followedOrNot(articleId, false) {
                doFollowOrNot(it, followString, followingString)
            }
        }
    }

    private fun firebaseSetup() {
        viewModel.getArticleData(articleId) {article ->
            viewModel.articleVisited(article)
            binding.nsvRoot.visibility = View.VISIBLE
            binding.progress.visibility = View.GONE
            binding.tvTotalViews.text = article.userViewed.size.toString()
            glideImage(requireActivity(), binding.fullImg, article.image)

            glideImage(requireActivity(),binding.imgChannelLogo, article.authorProfile, true)
            glideImage(requireActivity(), binding.imgLogo, article.authorProfile, true)
            binding.tvName.text = article.authorName
            binding.tvFullHead.text = article.title
            binding.tvChannelName.text = article.authorName
            binding.tvChannelDesc.text = article.authorDescription
            binding.tvNewsDesc.text = Html.fromHtml(article.story, Html.FROM_HTML_MODE_LEGACY)
            binding.tvDaysAgo.text = calculateElapsedTime(article.time)

            val originalText = "More from ${article.authorName}"
            val truncatedText = if (originalText.length > 16) {
                "${originalText.substring(0, 17)}..."
            } else {
                originalText
            }

            binding.tvMoreFrom.text = truncatedText

            binding.tvMoreFrom.text = truncatedText
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
            if(it.size > 3)
                commentAdapter.updateUi(it.subList(0, 2))
            else
                commentAdapter.updateUi(it)
        }
    }

    override fun onItemClick(articleId: String) {
        TODO("Not yet implemented")
    }

    override fun onArticleSaveListener(selectedItems: MutableList<String>) {
        TODO("Not yet implemented")
    }
}
