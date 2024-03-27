package com.newsapp.presenter.screen.auth.interest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.newsapp.R
import com.newsapp.databinding.FragmentNewsFeedBinding
import com.newsapp.presenter.viewmodel.NewsInterestViewModel


class NewsInterestFragment : Fragment() {

    private lateinit var binding: FragmentNewsFeedBinding
    private val viewModel: NewsInterestViewModel by viewModels()
    private lateinit var interestAdapter: NewsInterestAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    private fun setUpUi() {
        interestAdapter = NewsInterestAdapter(requireContext(), viewModel.getInterests())
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

        binding.recyclerView.adapter = interestAdapter
        binding.btnNewFeed.btnAllInOne.setOnClickListener {
            val interest = interestAdapter.getSelectedInterest()
            if (interest.isNotEmpty()) {
                viewModel.saveUserInterests(interest)
                findNavController().navigate(R.id.public_Profile_Fragment)
            }else{
                Toast.makeText(requireContext(), "Please select atleast one interest.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.ivArrowFeed.setOnClickListener { // navigate previous fragment
            findNavController().navigateUp()
        }
    }
}