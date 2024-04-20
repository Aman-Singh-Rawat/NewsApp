package com.newsapp.presenter.screen.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.newsapp.databinding.FragmentBookmarkBinding
import com.newsapp.presenter.screen.recentstories.TagsRecyclerView
import com.newsapp.util.OnTextSelectedListener

class BookmarkFragment : Fragment(), OnTextSelectedListener {
    private lateinit var binding: FragmentBookmarkBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarkBinding.inflate(
            inflater, container, false
        )
        setUpStoriesTag()
        return binding.root
    }
    private fun setUpStoriesTag() {
        val tagsRecyclerView = TagsRecyclerView(featureList(), true, requireActivity(), findNavController(), this)
        binding.rvBookmarkTag.adapter = tagsRecyclerView
    }
    private fun featureList(): List<String> {
        return listOf(
            "All","Politics","Technology","Business"
        )
    }

    override fun onTextSelected(topic: String) {
        val x = topic
    }
}