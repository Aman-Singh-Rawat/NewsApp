package com.newsapp.presenter.screen.auth

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.databinding.FragmentProfilePublicBinding
import com.newsapp.util.PrefKeys.USER
import com.newsapp.util.SharedPrefsManager

class PublicProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfilePublicBinding
    private var tvName: TextView? = null
    private var tvUserName: TextView? = null
    private var etName: EditText? = null
    private var etUserName: EditText? = null
    private var btnFinish: Button? = null
    private var tvBio: TextView? = null
    private var etBio: EditText? = null
    private val prefs by lazy { SharedPrefsManager.getInstance(requireContext()) }

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
        tvBio = binding.includeBio.root.findViewById(R.id.tvBio)
        etBio = binding.includeBio.root.findViewById(R.id.etBio)
        tvName = binding.includeProfile.root.findViewById(R.id.tvEmail)
        tvUserName = binding.includeProfile.root.findViewById(R.id.tvPassword)
        etName = binding.includeProfile.root.findViewById(R.id.etFillEmail)
        etUserName = binding.includeProfile.root.findViewById(R.id.etFillPassWord)

        changingValue()
    }

    @SuppressLint("SetTextI18n")
    private fun changingValue() {
        tvName?.text = "Full Name"
        tvUserName?.text = "UserName"
        etName?.hint = "e.g. John"
        etUserName?.hint = "john@gmail.com"
        tvBio?.text = "Bio"
        etBio?.hint = "Tech enthusiast, likes to share stories a..."
        etBio?.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)
        etBio?.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
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
            R.id.all_Set_Fragment
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivProfile.setOnClickListener {
            uploadImage(binding.ivProfile)
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
    private fun setData() {
        val saveData = prefs.getString()
    }


}


