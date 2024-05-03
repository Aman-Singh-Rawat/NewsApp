package com.newsapp.presenter.screen.bookmark

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.newsapp.R
import com.newsapp.databinding.FragmentBookMarkBottomSheetBinding
import com.newsapp.presenter.viewmodel.BookmarkViewModel
import com.newsapp.util.OnTextSelectedListener


class BookMarkBottomSheetFragment : BottomSheetDialogFragment(), OnTextSelectedListener {
    private lateinit var binding: FragmentBookMarkBottomSheetBinding
    private val viewModel by activityViewModels<BookmarkViewModel>()
    private val articleId by lazy {arguments?.getString("articleId") ?: ""}
    val list: MutableList<String> = mutableListOf()

    private val bottomSheetAdapter: BottomSheetAdapter = BottomSheetAdapter(this)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookMarkBottomSheetBinding.inflate(
            inflater, container, false
        )
        viewModel.getBookmarkCategory()
        return binding.root
    }

    private fun recyclerFunctionality() {
        binding.rvSelected.adapter = bottomSheetAdapter
        binding.rvSelected.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        viewModel.bookmarkCategory.observe(requireActivity(), Observer {
            bottomSheetAdapter.update(it)
        })
    }

    private fun printToastInCenter() {
        val toast = Toast(requireActivity())
        val customToast = layoutInflater.inflate(R.layout.dialog_save, null)
        toast.view = customToast
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.duration = Toast.LENGTH_LONG
        toast.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textChangeButton()

        recyclerFunctionality()

        binding.includeBookButton.btnOnboardingSkip.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.includeBookButton.btnOnboardingContinue.setOnClickListener {
            Log.d("debugging", "btnOnboardingContinue")
//            if (list.isNotEmpty()) {
//                viewModel.doArticleSave(articleId, list) {
//                    printToastInCenter()
//                    findNavController().navigateUp()
//                }
//            }
        }

        binding.ivAdd.setOnClickListener {
            findNavController().navigate(R.id.collectionBottomFragment)
        }
    }
    private fun textChangeButton() {
        binding.includeBookButton.btnOnboardingSkip.text = resources.getString(R.string.cancle)
        binding.includeBookButton.btnOnboardingContinue.text = resources.getString(R.string.done)
    }

    override fun onTextSelected(topic: String) {

        if (topic.endsWith("true")) {
            list.add(topic.substringBefore("true"))
        } else if (topic.endsWith("false")) {
            val string = topic.substringBefore("false")

            if (list.contains(string)) {
                list.remove(topic.substringBefore("false"))
            }
        }
    }
}

