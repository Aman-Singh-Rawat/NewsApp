package com.newsapp.presenter.screen.recentstories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.newsapp.R
import com.newsapp.core.base.BaseFragment
import com.newsapp.data.models.Article
import com.newsapp.databinding.FragmentRecentStoriesBinding
import com.newsapp.presenter.screen.profile.ProfileAdapter
import com.newsapp.presenter.viewmodel.CreateArticleViewModel


class RecentStoriesFragment : BaseFragment() {
    private lateinit var binding: FragmentRecentStoriesBinding
    private val viewModel by activityViewModels<CreateArticleViewModel>()
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
        val recentList: MutableList<Article> = mutableListOf()
        viewModel.getArticleData { articleList ->
            for (article in articleList) {
                recentList.add(article)
            }
            binding.rvNewsArticles.adapter = ProfileAdapter(recentList, requireContext(), this)
        }
    }

    private fun setupTagRecycler() {
        binding.rvRecentTag.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        binding.rvRecentTag.adapter = TagsRecyclerView(tagList())
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
   

}