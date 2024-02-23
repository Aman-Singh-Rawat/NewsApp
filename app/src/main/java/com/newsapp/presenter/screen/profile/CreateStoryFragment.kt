package com.newsapp.presenter.screen.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import jp.wasabeef.richeditor.RichEditor


class CreateStoryFragment : Fragment() {

    private var mEditor: RichEditor? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_story, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mEditor = view.findViewById<View>(jp.wasabeef.richeditor.R.id.editor) as RichEditor
        mEditor!!.setEditorHeight(200)
        mEditor!!.setEditorFontSize(22)
//        mEditor!!.setEditorFontColor(Color.RED)
        //mEditor.setEditorBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        //mEditor.setEditorBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        mEditor!!.setPadding(10, 10, 10, 10)
        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        mEditor!!.setPlaceholder("Write your story here")
        //mEditor.setInputEnabled(false);
        //mEditor.setInputEnabled(false);

        view.findViewById<View>(jp.wasabeef.richeditor.R.id.action_bold)
            .setOnClickListener(View.OnClickListener { mEditor!!.setBold() })

        view.findViewById<View>(jp.wasabeef.richeditor.R.id.action_italic)
            .setOnClickListener(View.OnClickListener { mEditor!!.setItalic() })

        view.findViewById<View>(jp.wasabeef.richeditor.R.id.action_underline)
            .setOnClickListener(View.OnClickListener { mEditor!!.setUnderline() })

        view.findViewById<View>(jp.wasabeef.richeditor.R.id.action_insert_bullets)
            .setOnClickListener(View.OnClickListener { mEditor!!.setBullets() })

        view.findViewById<View>(jp.wasabeef.richeditor.R.id.action_insert_numbers)
            .setOnClickListener(View.OnClickListener { mEditor!!.setNumbers() })
        view.findViewById<View>(jp.wasabeef.richeditor.R.id.action_insert_image)
            .setOnClickListener(View.OnClickListener {
               mEditor!!.insertImage(
                "https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg",
                "dachshund", 320
                )
            })

        view.findViewById<View>(jp.wasabeef.richeditor.R.id.action_insert_link).setOnClickListener(View.OnClickListener {
            mEditor!!.insertLink(
                "https://github.com/wasabeef",
                "wasabeef"
            )
        })

        val tvPreview = view.findViewById<TextView>(R.id.tvPreview)
        tvPreview.setOnClickListener {
            findNavController().navigate(R.id.previewStoryFragment)
        }
        val imgBackArrow = view.findViewById<ImageView>(R.id.imgBackArrow)
        imgBackArrow.setOnClickListener {
            findNavController().navigateUp()
        }
        val cvImage = view.findViewById<CardView>(R.id.cvImage)
        val ivStory = view.findViewById<ImageView>(R.id.ivStory)

        cvImage.setOnClickListener {
            uploadImage(ivStory)
        }
    }

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
}