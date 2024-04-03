package com.newsapp.presenter.screen.profile

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.newsapp.R
import com.newsapp.data.models.Article
import com.newsapp.data.models.User
import com.newsapp.databinding.FragmentProfileBinding
import com.newsapp.presenter.screen.recentstories.NewsArticlesRecyclerView
import com.newsapp.presenter.screen.recentstories.RecentDataClass
import com.newsapp.presenter.viewmodel.CreateArticleViewModel
import com.newsapp.util.SharedPrefsManager

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    private val prefs by lazy { SharedPrefsManager.getInstance(requireContext()) }
    private val viewModel by activityViewModels<CreateArticleViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding
            .inflate(inflater, container, false
            )

        return binding.root
    }
    private fun navigateAnotherActivity() {
        findNavController().navigate(R.id.editProfileFragment)
    }

    private fun setUpStories() {
        binding.rvProfileNews.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )

        val recentList: MutableList<Article> = mutableListOf()
        viewModel.getArticleData { articleList ->
            for (article in articleList) {
                recentList.add(article)
            }
            binding.rvProfileNews.adapter = ProfileAdapter(recentList, requireContext())
        }

    }
    private fun insertInTagsRV(): List<RecentDataClass> {
        val list =  listOf(
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
        val recentList: MutableList<RecentDataClass> = mutableListOf()
        viewModel.getArticleData { articleList ->
            for (index in articleList.indices) {
                val recentData = RecentDataClass(tvHeadline = articleList[index].title, list[index].ivNewsImg,
                    list[index].tvChannelName, list[index].imgChannelLogo, list[index].tvDaysAgo,
                    list[index].tvTotalViews, list[index].tvTotalComments)

                recentList.add(recentData)
            }
        }
        return recentList
    }
    private fun fabColorChange() {
        val color = ContextCompat.getColor(requireContext(), R.color.white)
        binding.fbAddStory.imageTintList = ColorStateList.valueOf(color)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fbAddStory.setOnClickListener{
            findNavController().navigate(R.id.navigation_CreateStory)
        }
        fabColorChange()
        setUpStories()

        binding.editProfile.setOnClickListener {
            navigateAnotherActivity()
        }
        binding.icLogout.setOnClickListener {
            findNavController().navigate(R.id.logoutFragment)
        }
        setUpUi()
    }

    private fun setUpUi() {
        val user = prefs.getUser()
        binding.tvProfileName.text = user?.fullName
        binding.tvPersonEmail.text = user?.userName
        binding.tvProfileDesc.text = user?.bio
        binding.tvWebsite.text = user?.website
        if (user?.profile != null && user.profile != "") {
            glideImage(user)
        }
    }

    private fun glideImage(user: User) {
        Glide.with(requireContext())
            .load(user.profile!!.toUri())
            .into(binding.cvPageProfile)
    }
}