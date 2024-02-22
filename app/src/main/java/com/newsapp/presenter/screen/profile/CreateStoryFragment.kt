package com.newsapp.presenter.screen.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.google.android.gms.cast.framework.media.ImagePicker
import com.newsapp.R
import java.net.URI


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