package com.newsapp.ui.bookMarkNav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.newsapp.R
import com.newsapp.databinding.FragmentCollectionBottomBinding

class CollectionBottomFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentCollectionBottomBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCollectionBottomBinding.inflate(
            inflater, container, false
        )

        buttonNameChange()
        return binding.root
    }
    private fun buttonNameChange() {
        binding.includeButtons.btnOnboardingSkip.text = "Cancel"
        binding.includeButtons.btnOnboardingContinue.text = "Done"
    }
}