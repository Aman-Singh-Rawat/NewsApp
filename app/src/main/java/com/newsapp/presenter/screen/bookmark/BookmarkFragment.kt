package com.newsapp.presenter.screen.bookmark

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.newsapp.databinding.FragmentBookmarkBinding
import com.newsapp.presenter.screen.recentstories.TagsRecyclerView
import com.newsapp.presenter.viewmodel.BookmarkViewModel
import com.newsapp.util.OnTextSelectedListener

class BookmarkFragment : Fragment(), OnTextSelectedListener {
    private lateinit var binding: FragmentBookmarkBinding
    private val viewModel by activityViewModels<BookmarkViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarkBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpStoriesTag()
    }

    private fun setUpStoriesTag() {
        viewModel.getBookmarkList {
            Log.d("bookmarkList", it.toString())
            val tagsRecyclerView = TagsRecyclerView(it, true, requireActivity(), findNavController(), this)
            binding.rvBookmarkTag.adapter = tagsRecyclerView
        }
    }
    override fun onTextSelected(topic: String) {
        val x = topic
    }
}