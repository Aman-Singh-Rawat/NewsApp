package com.newsapp.ui.home.Trending

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R

class TrendingFragment : Fragment() {
    private lateinit var nAdapter: TrendingAdapter
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trending, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       recyclerView.adapter = nAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        nAdapter.updateUi(getTrendingdata())
    }
    private fun getTrendingdata():List<TrendingDataClass>{
        return listOf(

        )
    }

}