package com.newsapp.ui.homeNav.homepage


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R
import com.newsapp.databinding.FragmentHomePageBinding
import com.newsapp.ui.homeNav.recentstories.NewsArticlesRecyclerView
import com.newsapp.ui.homeNav.recentstories.RecentDataClass
import com.newsapp.ui.homeNav.recentstories.TagsRecyclerView
import com.newsapp.util.SharedPrefsManager

class HomePageFragment : Fragment() {
    private lateinit var binding: FragmentHomePageBinding
    private val user by lazy { SharedPrefsManager.getInstance(requireContext()).getUser() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomePageBinding.inflate(
            inflater, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvPersonName.text = user?.fullName?:""
        setUpTrendRecycler()
        setUpStoriesTag()
        setUpStories()
        navigation()
    }


    private fun setUpLinearLayout(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )
    }

    // Recent Stories
    private fun setUpStories() {
        binding.rvNewsGroups.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        binding.rvNewsGroups.adapter = NewsArticlesRecyclerView(insertInTagsRV())

    }

    // Recent Stories Types
    private fun setUpStoriesTag() {

        setUpLinearLayout(binding.recyclerTag)
        binding.recyclerTag.adapter = TagsRecyclerView(featureList())
    }



    // Trending Recycler View
    private fun setUpTrendRecycler() {
        setUpLinearLayout(binding.rvTrending)
        binding.rvTrending.adapter = HpTrendRecycler(insertInTagsRV())
    }

    /* Its return List of NewsArticlesRecycler */
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
            ))
    }

    /* return feature list */
    private fun featureList(): List<String> {
        return listOf(
            "All","Politics","Technology","Business"
        )
    }

    private fun navigation() {
        binding.tvViewAll.setOnClickListener {
            findNavController().navigate(R.id.trendingFragment)
        }
        binding.tvViewAllRecent.setOnClickListener {
            findNavController()
                .navigate(R.id.recentStoriesFragment)
        }
    }

}