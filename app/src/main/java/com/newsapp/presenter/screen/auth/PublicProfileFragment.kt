package com.newsapp.presenter.screen.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.google.android.gms.cast.framework.media.ImagePicker
import com.newsapp.R
import com.newsapp.databinding.FragmentProfileBinding
import com.newsapp.databinding.FragmentProfilePublicBinding

class PublicProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfilePublicBinding
    private var tvName: TextView? = null
    private var tvUserName: TextView? = null
    private var etName: EditText? = null
    private var etUserName: EditText? = null
    private var btnFinish: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfilePublicBinding.inflate(
            inflater, container, false
        )
        btnFinish = binding.includeProfileFragment.root.findViewById(R.id.btnAllInOne)
        btnFinish?.text = "Finish"

        btnFinish?.setOnClickListener {
            openAllSetFragment()
        }
        binding.ivBackArrowProfile.setOnClickListener {
            onBackPressed()
        }
        initializingInclude()


        return binding.root

    }

    private fun initializingInclude() {
        tvName = binding.includeProfile.root.findViewById(R.id.tvEmail)
        tvUserName = binding.includeProfile.root.findViewById(R.id.tvPassword)
        etName = binding.includeProfile.root.findViewById(R.id.etFillEmail)
        etUserName = binding.includeProfile.root.findViewById(R.id.etFillPassWord)
        changingValue()
    }

    private fun changingValue() {
        tvName?.text = "Full Name"
        tvUserName?.text = "UserName"
        etName?.hint = "e.g. John"
        etUserName?.hint = "john@gmail.com"
        etName?.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
        etUserName?.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
        etName?.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)
        etUserName?.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)

    }

    val onBackPressed = {
        findNavController().navigateUp()
        true
    }
    val openAllSetFragment = {
        findNavController().navigate(
            R.id.action_public_Profile_Fragment_to_all_Set_Fragment
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cardViewProfile = view.findViewById<CardView>(R.id.cardViewProfile)
        val ivProfile = view.findViewById<ImageView>(R.id.ivProfile)
        cardViewProfile.setOnClickListener {
            uploadImage(ivProfile)
        }


    }

    private fun uploadImage(ivProfile: ImageView?) {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val ivProfile = view?.findViewById<ImageView>(R.id.ivProfile)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                val uri = data?.data
                ivProfile?.setImageURI(uri)
            }


        }
    }

}


