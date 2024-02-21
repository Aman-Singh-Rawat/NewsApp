package com.newsapp.presenter.screen.profile

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import java.io.IOException


class CreateStoryFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.newsapp.R.layout.fragment_create_story, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvPreview = view.findViewById<TextView>(com.newsapp.R.id.tvPreview)



        tvPreview.setOnClickListener {
            findNavController().navigate(com.newsapp.R.id.action_navigation_dashboard_to_previewStoryFragment)
        }
        val cvImage = view.findViewById<CardView>(com.newsapp.R.id.cvImage)
        val ivStory = view.findViewById<ImageView>(com.newsapp.R.id.ivStory)

        cvImage.setOnClickListener {
            uploadImage(ivStory)
        }



    }

        var editor = findViewById(R.id.editor) as Editor
        findViewById(com.newsapp.R.id.action_h1).setOnClickListener(View.OnClickListener {
            editor.updateTextStyle(
                EditorTextStyle.H1
            )
        })
        findViewById(com.newsapp.R.id.action_h2).setOnClickListener(View.OnClickListener {
            editor.updateTextStyle(
                EditorTextStyle.H2
            )
        })
        findViewById(com.newsapp.R.id.action_h3).setOnClickListener(View.OnClickListener {
            editor.updateTextStyle(
                EditorTextStyle.H3
            )
        })
        findViewById(com.newsapp.R.id.action_bold).setOnClickListener(View.OnClickListener {
            editor.updateTextStyle(
                EditorTextStyle.BOLD
            )
        })
        findViewById(com.newsapp.R.id.action_Italic).setOnClickListener(View.OnClickListener {
            editor.updateTextStyle(
                EditorTextStyle.ITALIC
            )
        })
        findViewById(com.newsapp.R.id.action_indent).setOnClickListener(View.OnClickListener {
            editor.updateTextStyle(
                EditorTextStyle.INDENT
            )
        })
        findViewById(com.newsapp.R.id.action_outdent).setOnClickListener(View.OnClickListener {
            editor.updateTextStyle(
                EditorTextStyle.OUTDENT
            )
        })
        findViewById(com.newsapp.R.id.action_bulleted).setOnClickListener(View.OnClickListener {
            editor.insertList(
                false
            )
        })
        findViewById(com.newsapp.R.id.action_color).setOnClickListener(View.OnClickListener {
            editor.updateTextColor(
                "#FF3333"
            )
        })
        findViewById(com.newsapp.R.id.action_unordered_numbered).setOnClickListener(View.OnClickListener {
            editor.insertList(
                true
            )
        })
        findViewById(com.newsapp.R.id.action_hr).setOnClickListener(View.OnClickListener { editor.insertDivider() })
        findViewById(com.newsapp.R.id.action_insert_image).setOnClickListener(View.OnClickListener { editor.openImagePicker() })
        findViewById(com.newsapp.R.id.action_insert_link).setOnClickListener(View.OnClickListener { editor.insertLink() })
        findViewById(com.newsapp.R.id.action_erase).setOnClickListener(View.OnClickListener { editor.clearAllContents() })
        findViewById(com.newsapp.R.id.action_blockquote).setOnClickListener(View.OnClickListener {
            editor.updateTextStyle(
                EditorTextStyle.BLOCKQUOTE
            )
        })
        editor.render()
    }
    }

    fun uploadImage(ivStory: ImageView) {

        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        val ivStory = view?.findViewById<ImageView>(R.id.ivStory)
//        if (resultCode == RESULT_OK) {
//            if (requestCode == 1) {
//                val uri = data?.data
//                ivStory?.setImageURI(uri)
//            }
//        }
//    }
fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == editor.PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
        val uri = data.data
        try {
            val bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri)
            editor.insertImage(bitmap)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    } else if (resultCode == Activity.RESULT_CANCELED) {
         editor.RestoreState();
    }
}



