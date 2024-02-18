package com.newsapp.presenter.screen.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.newsapp.R
import com.newsapp.databinding.FragmentNewsFeedBinding
import com.newsapp.presenter.screen.auth.newsfeedrecycler.datamodel.NewsFeedClass
import com.newsapp.presenter.screen.auth.newsfeedrecycler.datamodel.NewsFeedRecycler


class NewsFeedFragment : Fragment() {
    private lateinit var binding: FragmentNewsFeedBinding
    private var btnNewFeed: Button? = null

    private val arrayList = ArrayList<NewsFeedClass>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewsFeedBinding.inflate(
            inflater, container, false
        )
        btnNewFeed = binding.btnNewFeed.root.findViewById(R.id.btnAllInOne)

        addValueOnList()
        val newsFeedRecycler = NewsFeedRecycler(arrayList)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerView.adapter = newsFeedRecycler

        btnNewFeed?.setOnClickListener {
            nextPage()
        }

        binding.ivArrowFeed.setOnClickListener { // navigate previous fragment
            onBackPressed()
        }

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
    private fun nextPage(){
        findNavController().navigate(R.id.action_newsFeedFragment_to_public_Profile_Fragment)
    }

    private val onBackPressed = {
        findNavController().navigateUp()
        true
    }

}