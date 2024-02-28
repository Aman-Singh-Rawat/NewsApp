package com.newsapp.ui.homeNav.trending

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.newsapp.R
import com.newsapp.databinding.FragmentTrendingBinding
import com.newsapp.ui.homeNav.recentstories.NewsArticlesRecyclerView
import com.newsapp.ui.homeNav.recentstories.RecentDataClass

class TrendingFragment : Fragment() {
    private lateinit var binding: FragmentTrendingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrendingBinding.inflate(
            inflater, container, false
        )
        setUpArticleRecycler()
        return binding.root
    }
    private fun setUpArticleRecycler() {
        binding.rvTrendArticles.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        binding.rvTrendArticles.adapter = NewsArticlesRecyclerView(findNavController(), insertInTagsRV())
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

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgTrendingBack.setOnClickListener {
            findNavController().navigateUp()
        }


    }


}