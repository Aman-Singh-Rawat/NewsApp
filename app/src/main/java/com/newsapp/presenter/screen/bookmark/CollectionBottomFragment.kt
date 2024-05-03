package com.newsapp.presenter.screen.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.newsapp.R
import com.newsapp.databinding.FragmentCollectionBottomBinding
import com.newsapp.presenter.viewmodel.BookmarkViewModel

class CollectionBottomFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentCollectionBottomBinding
    private val viewModel by activityViewModels<BookmarkViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCollectionBottomBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonNameChange()
        binding.includeButtons.btnOnboardingContinue.setOnClickListener {
            if (binding.etCollectionTitle.text?.isNotEmpty() != null) {
                viewModel.saveBookmarkCategory(
                    binding.etCollectionTitle.text.toString(),
                    onSuccess = {
                        binding.etCollectionTitle.setText("")
                        dismiss()
                    },
                    onFailure = {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                        dismiss()
                    })
            }
        }
    }

    private fun buttonNameChange() {
        binding.includeButtons.btnOnboardingSkip.text = requireActivity().applicationContext.resources.getString(R.string.cancle)
        binding.includeButtons.btnOnboardingContinue.text = requireActivity().applicationContext.resources.getString(R.string.done)
    }
}