package com.newsapp.presenter.screen.profile

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
    private val prefs by lazy { SharedPrefsManager.getInstance(requireActivity()) }
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
            requireActivity(), LinearLayoutManager.VERTICAL, false
        )

        val recentList: MutableList<Article> = mutableListOf()
        showProgress()
        viewModel.getArticleData { articleList ->
            for (article in articleList) {
                recentList.add(article)
            }
            binding.rvProfileNews.adapter = ProfileAdapter(recentList, requireActivity(), this)
            binding.tvTotalStories.text = recentList.size.toString()
        }
        hideProgress()
    }
    private fun fabColorChange() {
        val color = ContextCompat.getColor(requireActivity(), R.color.white)
        binding.fbAddStory.imageTintList = ColorStateList.valueOf(color)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fbAddStory.setOnClickListener{
            findNavController().navigate(R.id.navigation_CreateStory)
        }

        fabColorChange()
        setUpStories()

        binding.tvWebsite.setOnClickListener {
            val urlText = binding.tvWebsite.text.toString().trim()
            val uri = try {
                if (urlText.startsWith("http://") || urlText.startsWith("https://")) {
                    Uri.parse(urlText)
                } else {
                    Uri.parse("http://$urlText")
                }
            } catch (e: Exception) {
                // Handle the case where the text is not a valid URL
                Toast.makeText(requireActivity(), "Invalid URL: $urlText", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val searchIntent = Intent(Intent.ACTION_VIEW, uri)
            try {
                startActivity(searchIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(requireActivity(), "No browser app available", Toast.LENGTH_SHORT).show()
            }
        }
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

    override fun onArticleSaveListener(selectedItems: MutableList<String>) {
        val x = selectedItems
    }


}