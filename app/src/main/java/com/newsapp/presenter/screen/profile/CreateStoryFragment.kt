package com.newsapp.presenter.screen.profile

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.newsapp.MainActivity
import com.newsapp.R
import com.newsapp.presenter.screen.auth.CreateAccountFragment
import com.newsapp.presenter.screen.auth.SignInFragment


class CreateStoryFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_story, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvPreview = view.findViewById<TextView>(R.id.tvPreview)
//        val imgBackArrow = view.findViewById<ImageView>(R.id.imgBackArrow)
//        imgBackArrow.setOnClickListener {
//          val intent = Intent(requireContext(),MainActivity::class.java)
//            startActivity(intent)
//        }

        tvPreview.setOnClickListener {
            findNavController().navigate(R.id.action_createStoryFragment_to_previewStoryFragment)
        }

//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//
//            editor = (SharedPreferences.Editor) findViewById(R.id.editor);
//            findViewById(R.id.action_h1).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    editor.updateTextStyle(EditorTextStyle.H1);
//                }
//            });
//
//            findViewById(R.id.action_h2).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    editor.updateTextStyle(EditorTextStyle.H2);
//                }
//            });
//
//            findViewById(R.id.action_h3).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    editor.updateTextStyle(EditorTextStyle.H3);
//                }
//            });
//
//            findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    editor.updateTextStyle(EditorTextStyle.BOLD);
//                }
//            });
//
//            findViewById(R.id.action_Italic).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    editor.updateTextStyle(EditorTextStyle.ITALIC);
//                }
//            });
//
//            findViewById(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    editor.updateTextStyle(EditorTextStyle.INDENT);
//                }
//            });
//
//            findViewById(R.id.action_outdent).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    editor.updateTextStyle(EditorTextStyle.OUTDENT);
//                }
//            });
//
//            findViewById(R.id.action_bulleted).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    editor.insertList(false);
//                }
//            });
//
//            findViewById(R.id.action_color).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    editor.updateTextColor("#FF3333");
//                }
//            });
//
//            findViewById(R.id.action_unordered_numbered).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    editor.insertList(true);
//                }
//            });
//
//            findViewById(R.id.action_hr).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    editor.insertDivider();
//                }
//            });
//
//            findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    editor.openImagePicker();
//                }
//            });
//
//            findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    editor.insertLink();
//                }
//            });
//
//
//            findViewById(R.id.action_erase).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    editor.clearAllContents();
//                }
//            });
//
//            findViewById(R.id.action_blockquote).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    editor.updateTextStyle(EditorTextStyle.BLOCKQUOTE);
//                }
//            });
//
//            editor.render();
//
//        }
    }

}