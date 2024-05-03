package com.newsapp.presenter.screen.bookmark

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
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
        showProgress()
        viewModel.getBookmarkCategory()
        viewModel.getBookmarkArticles()
        setupObserver()
        val color = ContextCompat.getColor(requireActivity(), R.color.white)
        binding.fabButton.imageTintList = ColorStateList.valueOf(color)

        binding.fabButton.setOnClickListener {
            findNavController().navigate(R.id.collectionBottomFragment)
        }
    }

    private fun setupObserver() {
        viewModel.bookmarkCategory.observe(viewLifecycleOwner) {
            hideProgress()
            binding.rvBookmarkTag.adapter = tagsRecyclerView
            tagsRecyclerView.updateUi(it)
        }

        viewModel.bookmarkArticles.observe(viewLifecycleOwner) {
            hideProgress()
            //TODO update bookmarked article list in recycler here
            //TODO also check if list is empty show empty view otherwise article list view
        }
    }
    override fun onTextSelected(topic: String) {
        val x = topic
    }
}