package com.newsapp.presenter.screen.Profile

import android.content.Intent
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
//        val ivArrowBack = view.findViewById<ImageView>(R.id.ivArrowBack)
//        ivArrowBack.setOnClickListener {
//          val intent = Intent(requireContext(),MainActivity::class.java)
//            startActivity(intent)
//        }

        tvPreview.setOnClickListener {
            findNavController().navigate(R.id.action_createStoryFragment_to_previewStoryFragment)
        }
    }

}