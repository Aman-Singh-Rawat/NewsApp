package com.newsapp.ui.homeNav.NewsDetails

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.newsapp.R
import com.newsapp.databinding.FragmentFullDetailsBinding

class FullDeatilsFragment : Fragment() {
    private lateinit var binding: FragmentFullDetailsBinding
    private val newsAdapter: NewsAdapter = NewsAdapter()
    private val commentAdapter: CommentAdapter = CommentAdapter()
    private lateinit var rvComment: RecyclerView
    private lateinit var rvTag: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the main fragment layout
        binding = FragmentFullDetailsBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }
    //Todo

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvView.setOnClickListener {
            findNavController().navigate(R.id.commentFragment)
        }
        binding.ivbackArrow.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.ivBookMark.setOnClickListener {
            val bottomSheetView =
                layoutInflater.inflate(R.layout.fragment_book_mark_bottom_sheet, null)
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }
        rvTag = view.findViewById(R.id.rvTag)
        rvTag.adapter = newsAdapter
        rvTag.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        newsAdapter.update(getdata())

        rvComment = view.findViewById(R.id.rvComment)
        rvComment.adapter = commentAdapter
        rvComment.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        commentAdapter.updateUi(getComment())


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

    private fun getdata(): List<String> {
        return listOf(
            "politics", "corruption", "investiagtion", "crime", "government", "report", "cnn"
        )
    }
}
