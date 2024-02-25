package com.newsapp.ui.bookMarkNav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.databinding.FragmentBookmarkBinding
import com.newsapp.ui.homeNav.RecentStories.TagsRecyclerView

class BookmarkFragment : Fragment() {
    private lateinit var binding: FragmentBookmarkBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarkBinding.inflate(
            inflater, container, false
        )
        binding.fabButton.setOnClickListener {
            openBottomSheet()
        }
        setUpStoriesTag()
        return binding.root
    }
    private fun openBottomSheet() {
        findNavController().navigate(R.id.collectionBottomFragment)
    }

    private fun setUpStoriesTag() {

        binding.rvBookmarkTag.adapter = TagsRecyclerView(featureList())
    }

    private fun featureList(): List<String> {
        return listOf(
            "All","Politics","Technology","Business"
        )
    }
}