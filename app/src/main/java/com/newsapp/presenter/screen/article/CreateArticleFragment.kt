package com.newsapp.presenter.screen.article

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.core.base.BaseFragment
import com.newsapp.databinding.FragmentCreateArticleBinding
import com.newsapp.presenter.viewmodel.CreateArticleViewModel


class CreateArticleFragment : BaseFragment() {

    private lateinit var binding: FragmentCreateArticleBinding
    private val viewModel by activityViewModels<CreateArticleViewModel>()
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        setData()
    }

    private fun setData() {
        viewModel.getArticle()?.let { article ->
            binding.etFillTitle.setText(article.title)
            binding.editor.html = article.story
        }
    }

    private fun setUpUi() {
        binding.run {
            editor.apply {
                setEditorFontColor(Color.BLACK)
                setEditorHeight(200)
                setEditorFontSize(18)
                setPadding(10, 10, 10, 10)
                setPlaceholder("Write your story here")
            }
            actionBold.setOnClickListener { editor.setBold() }
            actionItalic.setOnClickListener { editor.setItalic() }
            actionUnderline.setOnClickListener { editor.setUnderline() }
            actionInsertBullets.setOnClickListener { editor.setBullets() }
            actionInsertNumbers.setOnClickListener { editor.setNumbers() }

            tvPreview.setOnClickListener {
                if (validationOfViews() && imageUri != null) {
                    viewModel.imageUri = imageUri
                    viewModel.addArticle(
                        "",
                        title = etFillTitle.text.toString(),
                        story = editor.html
                    )
                    findNavController().navigate(R.id.previewStoryFragment)


                } else {
                    Toast.makeText(
                        requireContext(),
                        "Please do not be lazy input all Values",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            imgBackArrow.setOnClickListener { onBackPress() }
            cvImage.setOnClickListener { resultLauncher.launch("image/*") }
        }
    }

    private fun validationOfViews(): Boolean {
        val editorText = binding.editor.html
        return binding.etFillTitle.text.toString().isNotEmpty() &&
                editorText.isNotEmpty()
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {

        imageUri = it
        //viewModel.uploadImageToFirebase(it!!)
        binding.ivStory.setImageURI(it)
    }

    override fun onBackPress() {
        viewModel.clearArticleData()
        super.onBackPress()
    }
}

/*viewModel.uploadImageToFirebase(imageUri!!) {
                        imageUri ->
                        if (imageUri != null) {
                            viewModel.addArticle(
                                imageUri,
                                title = etFillTitle.text.toString(),
                                story = editor.html
                            )
                            findNavController().navigate(R.id.previewStoryFragment)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Failed to upload image",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }*/