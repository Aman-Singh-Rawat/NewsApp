package com.newsapp.presenter.screen.profile

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.newsapp.R
import com.newsapp.core.base.BaseFragment
import com.newsapp.data.models.Article
import com.newsapp.data.models.User
import com.newsapp.databinding.FragmentProfileBinding
import com.newsapp.presenter.viewmodel.ArticleDetailViewModel
import com.newsapp.presenter.viewmodel.CreateArticleViewModel
import com.newsapp.util.OnItemClickListener
import com.newsapp.util.SharedPrefsManager
import com.newsapp.util.glideImage

class ProfileFragment : BaseFragment(), OnItemClickListener {
    private val profileAdapter = ProfileAdapter(this)
    lateinit var binding: FragmentProfileBinding
    private val prefs by lazy { SharedPrefsManager.getInstance(requireActivity()) }
    private val viewModel by activityViewModels<CreateArticleViewModel>()
    private val createArticleViewModel by activityViewModels<CreateArticleViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding
            .inflate(inflater, container, false
            )

        setUpStories(requireActivity())
        return binding.root
    }
    private fun setUpStories(activity: FragmentActivity) {
        binding.rvProfileNews.layoutManager = LinearLayoutManager(
            requireActivity(), LinearLayoutManager.VERTICAL, false
        )

        val recentList: MutableList<Article> = mutableListOf()
        viewModel.getArticleData { articleList ->

            activity.runOnUiThread {
                for (article in articleList) {
                    recentList.add(article)
                }
                binding.rvProfileNews.adapter = profileAdapter
                profileAdapter.updateUi(recentList, "true", activity)
                binding.tvTotalStories.text = recentList.size.toString()
            }
        }
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
            findNavController().navigate(R.id.editProfileFragment)
        }
        binding.icLogout.setOnClickListener {
            findNavController().navigate(R.id.logoutFragment)
        }
        setUpUi()
    }

    private fun setUpUi() {
        prefs.getUser()?.let { user ->
            binding.run {
                tvProfileName.text = user.fullName
                tvPersonEmail.text = user.userName
                tvProfileDesc.text = user.bio
                tvWebsite.text = user.website
                tvTotalFollowing.text = user.followingList.size.toString()
                tvFollowers.text = user.followerList.size.toString()

                if (user.profile != "") {
                    glideImage(requireActivity(), binding.cvPageProfile, user.profile, true)
                }
            }
        }
    }

    override fun onItemClick(articleId: String) {
        Log.d("articleId", "articleId in onItemClick is:: $articleId")
        if ("shareArticles" in articleId) {
            val anotherPart = articleId.substringBefore("shareArticles")
            shareArticle(anotherPart)
        } else if ("editStory" in articleId) {
            val anotherPart = articleId.substringBefore("editStory")
            findNavController().navigate(R.id.navigation_CreateStory, bundleOf(
                "articleId" to anotherPart))
        } else if ("deleteStory" in articleId) {
            setUpAlertDialog(articleId)
        } else {
            findNavController().navigate(R.id.articleDetailsFragment, bundleOf(
                "articleId" to articleId))
        }
    }

    private fun shareArticle(anotherPart: String) {
        val deepLinkPendingIntent = NavDeepLinkBuilder(requireContext())
            .setGraph(R.navigation.mobile_navigation)
            .setDestination(R.id.articleDetailsFragment)
            .setArguments(bundleOf("articleId" to anotherPart))
            .createPendingIntent()

        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, deepLinkPendingIntent.intentSender)
        }

        startActivity(Intent.createChooser(intent, "Share Article"))
    }

    private fun setUpAlertDialog(articleId: String) {
        val anotherPart = articleId.substringBefore("deleteStory")
        val alertDialog = AlertDialog.Builder(requireActivity())
        alertDialog.setTitle("Confirm Delete...")
        alertDialog.setMessage("Are you sure you want delete this Article?")
        alertDialog.setCancelable(false)

        alertDialog.setIcon(R.drawable.ic_alert)
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                createArticleViewModel.deleteArticle(anotherPart) {
                }
            })
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
        alertDialog.show()
    }

    override fun onArticleSaveListener(selectedItems: MutableList<String>) {
        val x = selectedItems
    }
}