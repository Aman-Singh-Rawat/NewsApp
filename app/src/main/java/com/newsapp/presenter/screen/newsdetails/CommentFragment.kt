package com.newsapp.presenter.screen.newsdetails

import com.newsapp.presenter.viewmodel.CommentViewModel
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.newsapp.R
import com.newsapp.databinding.FragmentCommentBinding

class CommentFragment : Fragment() {
    private lateinit var binding: FragmentCommentBinding
    private val adapter: CommentAdapter = CommentAdapter()
    private val articleId by lazy {arguments?.getString("articleId") ?: ""}
    private val viewModel by activityViewModels<CommentViewModel>()

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
        binding.ivSendBtn.setOnClickListener {
            viewModel.setData(binding.etCommentTitle.text.toString(), articleId) {
                binding.etCommentTitle.setText("")
                rvCommentSetup()
            }
        }
    }

    private fun rvCommentSetup() {
        binding.rvCommentScreen.layoutManager =
            LinearLayoutManager(
                requireActivity(), LinearLayoutManager.VERTICAL,
                false
            )

        binding.rvCommentScreen.adapter = adapter
        viewModel.getComments(articleId) {
            Log.d("comments", it.toString())
            adapter.updateUi(it)
        }
    }
}