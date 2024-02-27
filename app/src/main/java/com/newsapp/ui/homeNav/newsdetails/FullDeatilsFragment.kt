package com.newsapp.ui.homeNav.newsdetails

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.newsapp.R
import com.newsapp.databinding.FragmentFullDetailsBinding

class FullDeatilsFragment : Fragment() {
    private lateinit var binding: FragmentFullDetailsBinding
    private val newsAdapter: NewsAdapter = NewsAdapter()
    private val commentAdapter: CommentAdapter = CommentAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the main fragment layout
        binding = FragmentFullDetailsBinding.inflate(
            inflater, container, false
        )

        binding.ivBookMark.setOnClickListener {
            findNavController().navigate(R.id.bookMarkBottomSheetFragment)
        }
        rvCommentSetup()
        rvNewsTags()
        return binding.root
    }
    private fun rvNewsTags() {
        binding.rvNewsTags.adapter = newsAdapter
        binding.rvNewsTags.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        newsAdapter.update(getdata())
    }
    private fun rvCommentSetup() {
        binding.rvComment.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,
                false
            )

        binding.rvComment.adapter = commentAdapter
        commentAdapter.updateUi(getComment())
    }

    private fun getdata(): List<String> {
        return listOf(
            "politics", "corruption", "investiagtion", "crime", "government", "report", "cnn"
        )
    }
    private fun getComment(): List<CommentData> {
        return listOf(
            CommentData(
                R.drawable.img_girl_profile,
                "Sanjuanita Ordonez",
                "3 day ago",
                "This investigative report is a powerful reminder of the importance of transparency and accountability in our political system.",
                "256"
            ),
            CommentData(
                R.drawable.imp_person_one,
                "Sanju Ordonez",
                "2 day ago",
                "This investigative report is a powerful reminder of the importance of transparency and accountability in our political system.",
                "36"
            ),
            CommentData(
                R.drawable.img_girl_profile,
                "anita Ordonez",
                "1 day ago",
                "This investigative report is a powerful reminder of the importance of transparency and accountability in our political system.",
                "144"
            )

        )
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ivBookMark = view.findViewById<ImageView>(R.id.ivBookMark)
        ivBookMark.setOnClickListener {
            val bottomSheetView =
                layoutInflater.inflate(R.layout.fragment_book_mark_bottom_sheet, null)
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }
        binding.rvNewsTags.adapter = newsAdapter
        rvTag.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        newsAdapter.update(getdata())

        binding.rvComment.adapter = commentAdapter
        rvComment.layoutManager =
            StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
        commentAdapter.updateUi(getComment())


    }*/

}
