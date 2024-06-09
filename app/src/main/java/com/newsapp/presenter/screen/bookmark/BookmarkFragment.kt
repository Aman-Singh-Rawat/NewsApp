package com.newsapp.presenter.screen.bookmark

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.newsapp.R
import com.newsapp.core.base.BaseFragment
import com.newsapp.databinding.FragmentBookmarkBinding
import com.newsapp.presenter.screen.profile.ProfileAdapter
import com.newsapp.presenter.screen.recentstories.TagsRecyclerView
import com.newsapp.presenter.viewmodel.BookmarkViewModel
import com.newsapp.util.OnItemClickListener
import com.newsapp.util.OnTextSelectedListener

class BookmarkFragment : BaseFragment(), OnTextSelectedListener, OnItemClickListener {
    private lateinit var binding: FragmentBookmarkBinding
    private val tagsRecyclerView = TagsRecyclerView(this)
    private val profileAdapter = ProfileAdapter(this)
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

        viewModel.bookmarkArticles.observe(viewLifecycleOwner) { savedList ->
            hideProgress()
            if (savedList.isNullOrEmpty())
                binding.emptyConstraint.visibility = View.VISIBLE
            else {
                binding.emptyConstraint.visibility = View.GONE
                binding.rvSavedArticles.adapter = profileAdapter
                profileAdapter.updateUi(savedList, "delete", requireContext())
            }

            //TODO update bookmarked article list in recycler here
            //TODO also check if list is empty show empty view otherwise article list view
        }
    }
    override fun onTextSelected(topic: String) {
        val x = topic
    }

    override fun onItemClick(articleId: String) {
        var flag = true
        val alertDialog = AlertDialog.Builder(requireActivity())
        alertDialog.setTitle("Confirm Delete...")
        alertDialog.setMessage("Are you sure you want delete this Article?")
        alertDialog.setCancelable(false)

        val negativeButton = alertDialog.setIcon(R.drawable.ic_alert)
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                viewModel.checkBookmark(articleId, {
                    setupObserver()
                    flag = it
                }) {
                    Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                }
                dialog.dismiss()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
        alertDialog.show()
        if (!flag)
            Snackbar.make(binding.root, "Deleted from Bookmark", Snackbar.LENGTH_LONG).show()
    }

    override fun onArticleSaveListener(selectedItems: MutableList<String>) {
        TODO("Not yet implemented")
    }
}