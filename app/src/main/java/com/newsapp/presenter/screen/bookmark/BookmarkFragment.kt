package com.newsapp.presenter.screen.bookmark

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.newsapp.R
import com.newsapp.core.base.BaseFragment
import com.newsapp.databinding.FragmentBookmarkBinding
import com.newsapp.presenter.screen.recentstories.TagsRecyclerView
import com.newsapp.presenter.viewmodel.BookmarkViewModel
import com.newsapp.util.OnTextSelectedListener

class BookmarkFragment : BaseFragment(), OnTextSelectedListener {
    private lateinit var binding: FragmentBookmarkBinding
    private val tagsRecyclerView = TagsRecyclerView(this)
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

        viewModel.getBookmarkList()
        setUpStoriesTag()

        val color = ContextCompat.getColor(requireActivity(), R.color.white)
        binding.fabButton.imageTintList = ColorStateList.valueOf(color)

        binding.fabButton.setOnClickListener {
            findNavController().navigate(R.id.collectionBottomFragment)
        }
    }

    private fun setUpStoriesTag() {
        showProgress()
        viewModel.bookmarkLiveData.observe(requireActivity(), Observer {
            binding.rvBookmarkTag.adapter = tagsRecyclerView
            tagsRecyclerView.updateUi(it)
        })
        hideProgress()
    }
    override fun onTextSelected(topic: String) {
        val x = topic
    }
}