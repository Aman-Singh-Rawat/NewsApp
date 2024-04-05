package com.newsapp.presenter.screen.profile

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.newsapp.R
import com.newsapp.core.base.BaseFragment
import com.newsapp.data.models.Article
import com.newsapp.data.models.User
import com.newsapp.databinding.FragmentProfileBinding
import com.newsapp.presenter.viewmodel.CreateArticleViewModel
import com.newsapp.util.OnItemClickListener
import com.newsapp.util.SharedPrefsManager
import com.newsapp.util.glideImage

class ProfileFragment : BaseFragment(), OnItemClickListener {
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
        showProgress()
        viewModel.getArticleData { articleList ->
            for (article in articleList) {
                recentList.add(article)
            }
            binding.rvProfileNews.adapter = ProfileAdapter(recentList, requireContext(), this)
        }
        hideProgress()
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
            glideImage(binding.cvPageProfile, user.profile)
        }
    }

    override fun onItemClick(articleId: String) {
        findNavController().navigate(R.id.articleDetailsFragment, bundleOf(
            "articleId" to articleId)
        )
    }

    override fun onArticleSaveListener(articleId: String) {
        findNavController().navigate(R.id.navigation_bookmark, bundleOf(
            "articleId" to articleId)
        )
    }

}