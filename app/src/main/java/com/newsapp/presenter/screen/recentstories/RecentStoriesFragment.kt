package com.newsapp.presenter.screen.recentstories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.newsapp.R
import com.newsapp.core.base.BaseFragment
import com.newsapp.data.models.Article
import com.newsapp.databinding.FragmentRecentStoriesBinding
import com.newsapp.presenter.screen.profile.ProfileAdapter
import com.newsapp.presenter.viewmodel.CreateArticleViewModel
import com.newsapp.presenter.viewmodel.HomePageViewModel
import com.newsapp.util.OnItemClickListener
import com.newsapp.util.OnTextSelectedListener

class RecentStoriesFragment : BaseFragment(), OnItemClickListener, OnTextSelectedListener {
    private lateinit var binding: FragmentRecentStoriesBinding
    private val viewModel by activityViewModels<HomePageViewModel>()
    private lateinit var profileAdapter: ProfileAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentRecentStoriesBinding.inflate(
            inflater, container, false
        )
        setupTagRecycler()
        setUp()
        return binding.root
    }
    private fun setUp() {
        binding.rvNewsArticles.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
    }

    private fun setupTagRecycler() {
        binding.rvRecentTag.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        binding.rvRecentTag.adapter = TagsRecyclerView(tagList(), this)
    }

    private fun tagList(): List<String> {
        return listOf("All", "Politics", "Technology", "Business")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgRecentBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
    override fun onItemClick(articleId: String) {
        findNavController().navigate(R.id.articleDetailsFragment, bundleOf(
            "articleId" to articleId)
        )
    }

    override fun onArticleSaveListener(selectedItems: MutableList<String>) {
        viewModel.saveArticle(selectedItems)
    }

    override fun onTextSelected(topic: String) {
        showProgress()
        viewModel.getSelectedData(topic) {
            val articleList: MutableList<Article> = mutableListOf()
            for (list in it) {
                articleList.add(list)
            }
            profileAdapter = ProfileAdapter(articleList, requireContext(), this)
            binding.rvNewsArticles.adapter = profileAdapter
            hideProgress()
        }
    }

}