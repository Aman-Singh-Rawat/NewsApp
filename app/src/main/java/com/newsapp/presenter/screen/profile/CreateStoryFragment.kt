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
import androidx.cardview.widget.CardView
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



        tvPreview.setOnClickListener {
            findNavController().navigate(R.id.action_createStoryFragment_to_previewStoryFragment)
        }
    }

//    private fun uploadImage(image: ImageView?) {
//        val cvImage = view.findViewById<CardView>(R.id.cvImage)
//        cvImage.setOnClickListener {
//            val ivStory = view.findViewById<ImageView>(R.id.ivStory)
//            uploadImage(ivStory)
//        }
//        val intent = Intent()
//        intent.action = Intent.ACTION_GET_CONTENT
//        intent.type = "ivStory/*"
//        startActivityForResult(intent,1)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(requestCode==1){
//            ivStory.setImageURI(data?.data)
//        }
//
//    }

}