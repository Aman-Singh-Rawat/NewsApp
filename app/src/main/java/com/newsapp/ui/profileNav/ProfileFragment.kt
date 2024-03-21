package com.newsapp.ui.profileNav

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.newsapp.MainActivity
import com.newsapp.R
import com.newsapp.databinding.FragmentProfileBinding
import com.newsapp.presenter.screen.auth.register.SignUp
import com.newsapp.ui.homeNav.recentstories.NewsArticlesRecyclerView
import com.newsapp.ui.homeNav.recentstories.RecentDataClass
import com.newsapp.util.PrefKeys
import com.newsapp.util.PrefKeys.BIO
import com.newsapp.util.PrefKeys.FULL_NAME
import com.newsapp.util.PrefKeys.USER_NAME
import com.newsapp.util.PrefKeys.WEBSITE
import com.newsapp.util.SharedPrefsManager

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    private val prefs by lazy { SharedPrefsManager.getInstance(requireContext()) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding
            .inflate(inflater, container, false
            )
        fabColorChange()
        setUpStories()

        binding.editProfile.setOnClickListener {
            navigateAnotherActivity()
        }
        binding.icLogout.setOnClickListener {
            findNavController().navigate(R.id.logoutFragment)
        }
        return binding.root
    }
    private fun navigateAnotherActivity() {
        findNavController().navigate(R.id.editProfileFragment)
    }

    private fun setUpStories() {
        binding.rvProfileNews.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        binding.rvProfileNews.adapter = NewsArticlesRecyclerView(insertInTagsRV())

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
    private fun fabColorChange() {
        val color = ContextCompat.getColor(requireContext(), R.color.white)
        binding.fbAddStory.imageTintList = ColorStateList.valueOf(color)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fbAddStory.setOnClickListener{
            findNavController().navigate(R.id.navigation_CreateStory)
        }
        setUpUi()
    }

    private fun setUpUi() {
        val user = prefs.getUser()
        binding.tvProfileName.text = user?.name
        binding.tvPersonEmail.text = user?.email
        binding.tvProfileDesc.text = user?.bio
        binding.tvWebsite.text = user?.website
    }
}