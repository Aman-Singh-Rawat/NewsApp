package com.newsapp.presenter.screen.auth

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.gms.cast.framework.media.ImagePicker
import com.newsapp.R
import com.newsapp.databinding.FragmentProfileBinding

class PublicProfileFragment : Fragment() {
    private lateinit var binding:FragmentProfileBinding
    private var tvName: TextView? = null
    private var tvUserName: TextView? = null
    private var etName: EditText? = null
    private var etUserName: EditText? = null
    private var btnFinish: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(
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



    }



}