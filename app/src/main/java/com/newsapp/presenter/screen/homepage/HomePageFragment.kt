package com.newsapp.presenter.screen.homepage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.newsapp.R
import com.newsapp.core.base.BaseFragment
import com.newsapp.data.models.Article
import com.newsapp.data.models.User
import com.newsapp.databinding.FragmentHomePageBinding
import com.newsapp.presenter.screen.profile.ProfileAdapter
import com.newsapp.presenter.screen.recentstories.RecentDataClass
import com.newsapp.presenter.screen.recentstories.TagsRecyclerView
import com.newsapp.presenter.viewmodel.CreateArticleViewModel
import com.newsapp.presenter.viewmodel.HomePageViewModel
import com.newsapp.util.OnItemClickListener
import com.newsapp.util.OnTextSelectedListener
import com.newsapp.util.SharedPrefsManager
import com.newsapp.util.glideImage

class HomePageFragment : BaseFragment(), OnItemClickListener, OnTextSelectedListener{
    private lateinit var profileAdapter: ProfileAdapter
    private lateinit var binding: FragmentHomePageBinding
    private val viewModel by activityViewModels<HomePageViewModel>()
    private val prefs by lazy { SharedPrefsManager.getInstance(requireContext().applicationContext) }

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

        setUpUi()
        setUpTrendRecycler()
        navigation()
        setUp()
        setUpHome()

    }
    private fun setUp() {
        binding.rvNewsGroups.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
    }
    private fun setUpHome(){
        binding.recyclerTag.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        binding.recyclerTag.adapter = TagsRecyclerView(featureList(), this)
    }

    private fun setUpUi() {
        val currentUser = prefs.getUser()
        if (currentUser != null) {
            binding.tvPersonName.text = currentUser.userName
            if (currentUser.profile != "") {
                glideImage(binding.cvPageProfile, currentUser.profile)
            }
        }
    }
    // Trending Recycler View
    private fun setUpTrendRecycler() {
        binding.rvTrending.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )
        binding.rvTrending.adapter = HpTrendRecycler(insertInTagsRV())
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
//    /* return feature list */
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

    override fun onBackPress(){
        requireActivity().finish()
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
             binding.rvNewsGroups.adapter = profileAdapter
             hideProgress()
         }
    }

}
