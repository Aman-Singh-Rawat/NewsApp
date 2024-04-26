package com.newsapp.presenter.screen.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.core.base.BaseFragment
import com.newsapp.data.models.User
import com.newsapp.databinding.FragmentUserProfileBinding
import com.newsapp.presenter.viewmodel.UserProfileViewModel
import com.newsapp.util.OnItemClickListener
import com.newsapp.util.SharedPrefsManager
import com.newsapp.util.glideImage

class UserProfileFragment : BaseFragment(), OnItemClickListener {
    private lateinit var binding: FragmentUserProfileBinding
    private val profileAdapter = ProfileAdapter(this)
    private val prefs by lazy { SharedPrefsManager.getInstance(requireActivity()) }
    private val articleId by lazy {arguments?.getString("articleId") ?: ""}
    private val viewModel by activityViewModels<UserProfileViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgBackArrow.setOnClickListener {
            findNavController().navigateUp()
        }
        userProfileSetUp()
    }

    private fun userArticles(user: User) {
        viewModel.getUserArticles(user.uid) {
            binding.rvProfileNews.adapter = profileAdapter
            profileAdapter.updateUi(it, false, requireActivity())
        }
    }

    private fun userProfileSetUp() {
        viewModel.getUserDetails(articleId) { user ->
            binding.apply {
                glideImage(requireActivity(), cvPageProfile, user.profile, true)
                tvProfileName.text = user.fullName
                tvPersonEmail.text = user.userName
                tvProfileDesc.text = user.bio
                tvWebsite.text = user.website
            }
            userArticles(user)
        }
    }

    override fun onItemClick(articleId: String) {
        TODO("Not yet implemented")
    }

    override fun onArticleSaveListener(selectedItems: MutableList<String>) {
        TODO("Not yet implemented")
    }
}