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
import com.newsapp.data.models.Article
import com.newsapp.databinding.FragmentRecentStoriesBinding
import com.newsapp.presenter.screen.profile.ProfileAdapter
import com.newsapp.presenter.viewmodel.CreateArticleViewModel

class RecentStoriesFragment : Fragment() {
    private lateinit var binding: FragmentRecentStoriesBinding
    private val viewModel by activityViewModels<CreateArticleViewModel>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentRecentStoriesBinding.inflate(
            inflater, container, false
        )
        setupTagRecycler()
//        setUpArticleRecycler()
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
            binding.rvNewsArticles.adapter = ProfileAdapter(recentList, requireContext())
        }
    }

//    private fun setUpArticleRecycler() {
//        binding.rvNewsArticles.layoutManager = LinearLayoutManager(
//            requireContext(), LinearLayoutManager.VERTICAL, false
//        )
//        binding.rvNewsArticles.adapter = NewsArticlesRecyclerView(insertInTagsRV())
//    }
    private fun setupTagRecycler() {
        binding.rvRecentTag.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        binding.rvRecentTag.adapter = TagsRecyclerView(tagList())
    }

    private fun tagList(): List<String> {
        return listOf("All", "Politics", "Technology", "Business")
    }
    private fun insertInTagsRV(): List<RecentDataClass> {
        return listOf(
            RecentDataClass(
                "Unmasking the Truth: Investigative Report Exposes Widespread Political Corrup",
                R.drawable.img_non_blur,
                "CNN News",
                R.drawable.ic_cnn_news,
                "6 day ago",
                "132.2k",
                "2.3k"
            ),
            RecentDataClass(
                "Breaking News: Political Agreement Reached, Aims to Reshape the Nation",
                R.drawable.img_non_blur,
                "USA Today",
                R.drawable.imp_person_one,
                "2 days ago",
                "193.3k",
                "2.4k"
            ),
            RecentDataClass(
                "Unmasking the Truth: Investigative Report Exposes Widespread Political Corrup",
                R.drawable.img_cute_girl_with_robot,
                "CNN News",
                R.drawable.ic_apple_logo,
                "6 day ago",
                "132.2k",
                "2.3k"
            ),
            RecentDataClass(
                "Breaking News: Political Agreement Reached, Aims to Reshape the Nation",
                R.drawable.img_non_blur,
                "USA Today",
                R.drawable.imp_person_one,
                "2 days ago",
                "193.3k",
                "2.4k"
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgRecentBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}