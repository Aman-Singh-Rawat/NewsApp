package com.newsapp.presenter.screen.article

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.databinding.FragmentCreateArticleBinding


class CreateArticleFragment : Fragment() {

    private lateinit var binding: FragmentCreateArticleBinding
    private val viewModel by viewModels<CreateArticleViewModel>()
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
        val article = viewModel.getArticle()
        binding.etFillTitle.setText(article.title)
        binding.editor.html = article.story
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
                if (validationOfViews()) {
                    viewModel.addArticle(imageUri.toString(), title = etFillTitle.toString(), story = editor.html)
                    findNavController().navigate(R.id.previewStoryFragment)
                } else {
                    Toast.makeText(requireContext(), "Please do not be lazy input all Values", Toast.LENGTH_LONG).show()
                }
            }

            imgBackArrow.setOnClickListener { findNavController().navigateUp() }
            cvImage.setOnClickListener { resultLauncher.launch("image/*") }
        }
    }
    private fun validationOfViews(): Boolean {
        val editorText = binding.editor.html

        return if (binding.etFillTitle.text.toString().isNotEmpty() &&
            editorText.isNotEmpty() && binding.ivStory.drawable != null) {
            true
        }
        else {
            false
        }
    }
    private val resultLauncher = registerForActivityResult(

        ActivityResultContracts.GetContent()) {
        imageUri = it
        binding.ivStory.setImageURI(it)
    }
}

/*
    fun uploadImage(ivStory: ImageView) {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val ivStory = view?.findViewById<ImageView>(R.id.ivStory)
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                val uri = data?.data
                ivStory?.setImageURI(uri)
            }
        }
    }
 */