package com.newsapp.ui.home.RecentStories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R
import com.newsapp.ui.home.Trending.TrendingData

class RecentStoriesFragment : Fragment() {
    private lateinit var fAdapter: FeatureAdapter
    private lateinit var rAdapter: RecentAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recent_stories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvFeature)
        recyclerView.adapter = fAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        fAdapter.updateUi(getFeaturedata())
        val recyclerView2 = view.findViewById<RecyclerView>(R.id.rvNewsArticle)
        recyclerView.adapter = rAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rAdapter.update(getRecentdata())

    }

    private fun getRecentdata(): List<RecentData> {
return listOf(
    RecentData(
        "Unmasking the Truth: Investigative Report Exposes Widespread Political Corrup",
        R.drawable.img_back,
        "CNN News",
        R.drawable.img_non_blur,
        "6 day ago",
        "132.2k",
        "2.3k"
    ),
    RecentData(
        "Unmasking the Truth: Investigative Report Exposes Widespread Political Corrup",
        R.drawable.img_back,
        "CNN News",
        R.drawable.img_non_blur,
        "6 day ago",
        "132.2k",
        "2.3k"
    ),
    RecentData(
        "Unmasking the Truth: Investigative Report Exposes Widespread Political Corrup",
        R.drawable.img_back,
        "CNN News",
        R.drawable.img_non_blur,
        "6 day ago",
        "132.2k",
        "2.3k"
    ),
    RecentData(
        "Unmasking the Truth: Investigative Report Exposes Widespread Political Corrup",
        R.drawable.img_back,
        "CNN News",
        R.drawable.img_non_blur,
        "6 day ago",
        "132.2k",
        "2.3k"
    ),
)
    }

    private fun getFeaturedata(): List<String> {
return listOf(
    "All","Politics","Technology","Business"
)
    }

}