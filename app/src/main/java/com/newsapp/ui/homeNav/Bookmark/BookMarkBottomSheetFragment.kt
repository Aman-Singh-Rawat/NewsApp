package com.newsapp.ui.homeNav.Bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.newsapp.R
import com.newsapp.databinding.FragmentBookMarkBottomSheetBinding
import com.newsapp.databinding.FragmentFullDetailsBinding

class BookMarkBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBookMarkBottomSheetBinding
    private val mAdapter: BottomSheetAdapter = BottomSheetAdapter()
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBookMarkBottomSheetBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSeleced
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mAdapter.update(getData())

        binding.btnCancle.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnDone.setOnClickListener {
            findNavController().navigate(R.id.saveDailogeFragment2)
        }
    }
    private fun getData(): List<String> {
        return listOf(
            "Reading List", "References"
        )
    }
}

