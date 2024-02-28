package com.newsapp.ui.homeNav.bookmark

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.newsapp.R
import com.newsapp.databinding.FragmentBookMarkBottomSheetBinding

class BookMarkBottomSheetFragment : DialogFragment() {
    private lateinit var binding: FragmentBookMarkBottomSheetBinding
    private val bottomSheetAdapter: BottomSheetAdapter = BottomSheetAdapter()
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBookMarkBottomSheetBinding.inflate(
            inflater, container, false
        )
        textChangeButton()
        return binding.root
    }
    @SuppressLint("SetTextI18n")
    private fun textChangeButton() {
        binding.includeBookButton.btnOnboardingSkip.text = "Cancel"
        binding.includeBookButton.btnOnboardingContinue.text = "Done"
    }
    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSeleced.adapter = bottomSheetAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        bottomSheetAdapter.update(getData())

        binding.includeBookButton.btnOnboardingSkip.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.includeBookButton.btnOnboardingContinue.setOnClickListener {
            val toast = Toast(requireContext())
            val customToast = layoutInflater.inflate(R.layout.dialog_save, null)
            toast.view = customToast
            toast.duration = Toast.LENGTH_SHORT
            toast.show()
        }
    }
    private fun getData(): List<String> {
        return listOf(
            "Reading List", "References"
        )
    }
}

