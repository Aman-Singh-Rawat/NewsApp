package com.newsapp.presenter.screen.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.newsapp.R
import com.newsapp.databinding.FragmentNewsFeedBinding
import com.newsapp.presenter.screen.auth.newsfeedrecycler.datamodel.NewsFeedClass
import com.newsapp.presenter.screen.auth.newsfeedrecycler.datamodel.NewsFeedRecycler

class NewsFeedFragment : Fragment() {
    private lateinit var binding: FragmentNewsFeedBinding
    private val arrayList = ArrayList<NewsFeedClass>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewsFeedBinding.inflate(
            inflater, container, false
        )
        addValueOnList()
        val newsFeedRecycler = NewsFeedRecycler(arrayList)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerView.adapter = newsFeedRecycler

        return binding.root
    }
    private fun addValueOnList() {
        arrayList.add(
            NewsFeedClass(R.drawable.img_politics,
                "Politics")
        )

        arrayList.add(
            NewsFeedClass(R.drawable.img_football,
                "Sport")
        )

        arrayList.add(
            NewsFeedClass(R.drawable.img_technology,
                "Technology")
        )

        arrayList.add(
            NewsFeedClass(R.drawable.img_science,
                "Science")
        )

        arrayList.add(
            NewsFeedClass(R.drawable.img_business,
                "Business")
        )

        arrayList.add(
            NewsFeedClass(R.drawable.img_fashion,
                "Fashion")
        )
        arrayList.add(
            NewsFeedClass(R.drawable.img_health,
                "Health")
        )

        arrayList.add(
            NewsFeedClass(R.drawable.img_entertainment,
                "Entertainment")
        )

        arrayList.add(
            NewsFeedClass(R.drawable.img_finance,
                "Finance")
        )
    }

}