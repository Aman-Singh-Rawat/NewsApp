package com.newsapp.presenter.screen.bookmark

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.newsapp.R
import com.newsapp.databinding.FragmentBookMarkBottomSheetBinding


class BookMarkBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBookMarkBottomSheetBinding
    private val bottomSheetAdapter: BottomSheetAdapter = BottomSheetAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBookMarkBottomSheetBinding.inflate(
            inflater, container, false
        )
        textChangeButton()

        binding.rvSelected.adapter = bottomSheetAdapter
        binding.rvSelected.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        bottomSheetAdapter.update(getData())

        binding.includeBookButton.btnOnboardingSkip.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.includeBookButton.btnOnboardingContinue.setOnClickListener {
            val toast = Toast(requireActivity())
            val customToast = layoutInflater.inflate(R.layout.dialog_save, null)
            toast.view = customToast
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.duration = Toast.LENGTH_SHORT
            toast.show()
        }

        return binding.root
    }
    @SuppressLint("SetTextI18n")
    private fun textChangeButton() {
        binding.includeBookButton.btnOnboardingSkip.text = "Cancel"
        binding.includeBookButton.btnOnboardingContinue.text = "Done"
    }

    private fun getData(): List<String> {
        return listOf(
            "Reading List", "References", "Reading List", "References", "Reading List", "References"
        )
    }
}

