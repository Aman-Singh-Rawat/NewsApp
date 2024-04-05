package com.newsapp.presenter.screen.newsdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.newsapp.R
import com.newsapp.databinding.FragmentCommentBinding

class CommentFragment : Fragment() {
    private lateinit var binding: FragmentCommentBinding
    private val adapter: CommentAdapter = CommentAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCommentBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ivArrow = view.findViewById<ImageView>(R.id.ivArrow)
        ivArrow.setOnClickListener {
            findNavController().navigateUp()
        }
        rvCommentSetup()
    }

    private fun rvCommentSetup() {
        binding.rvCommentScreen.layoutManager =
            LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL,
                false
            )
        binding.rvCommentScreen.adapter = adapter
        adapter.updateUi(getComment())
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
}