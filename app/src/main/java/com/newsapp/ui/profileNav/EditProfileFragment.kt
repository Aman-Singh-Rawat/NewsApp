package com.newsapp.ui.profileNav

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.databinding.FragmentEditProfileBinding
import com.newsapp.models.User
import com.newsapp.ui.profileNav.viewmodel.ViewModelProfile
import com.newsapp.util.SharedPrefsManager

class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private val viewModelProfile: ViewModelProfile by lazy {
        ViewModelProfile(requireActivity().application)
    }
    private val prefs by lazy { SharedPrefsManager.getInstance(requireContext().applicationContext) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding
            .inflate(inflater, container, false
            )

        binding.imgBackArrow.setOnClickListener {
            findNavController().navigateUp()
        }
        changeButtonText()
        textSetup()
        return binding.root

    }
    private fun uploadImage(imgEditProfile : ImageView) {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        startActivityForResult(intent, 5)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val imgEditProfile = view?.findViewById<ImageView>(R.id.imgEditProfile)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 5) {
                val uri = data?.data
                imgEditProfile?.setImageURI(uri)
            }
        }
    }
    private fun changeButtonText() {
        binding.includeBtn.btnAllInOne.text = "Save"

    }

    private fun textSetup() {
        /* Change TextView text */
        binding.includeEditFragment.tvEmail.text = "Full Name"
        binding.includeEditFragment.tvPassword.text = "Username"

        /* Remove Drawable Icon from Edittext */
        binding.includeEditFragment.etFillEmail
            .setCompoundDrawablesWithIntrinsicBounds(
                null, null, null, null
            )

        binding.includeEditFragment.etFillPassWord
            .setCompoundDrawablesWithIntrinsicBounds(
                null, null, null, null
            )

        /* Changing hint Type */
        binding.includeEditFragment.etFillEmail.hint = "e.g. John"
        binding.includeEditFragment.etFillPassWord.hint = "@andrew_ainsley"

        /* Changing input type */
        binding.includeEditFragment.etFillEmail.inputType =
            InputType.TYPE_TEXT_VARIATION_PERSON_NAME

        binding.includeEditFragment.etFillPassWord.inputType =
            InputType.TYPE_TEXT_VARIATION_PERSON_NAME
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivImageOpen.setOnClickListener {
            uploadImage(binding.imgEditProfile)
        }

        binding.includeBtn.btnAllInOne.setOnClickListener {
            dataSave()
            findNavController().navigateUp()
        }

    }

    private fun dataSave() {
        val fullName = binding.includeEditFragment.etFillEmail.text.toString()
        val name = binding.includeEditFragment.etFillPassWord.text.toString()
        val bio = binding.includeBio.etBio.text.toString()
        val website = binding.etWebsite.text.toString()

        viewModelProfile.setData(fullName, name, bio, website)

        prefs.saveUser(User(name = fullName, email = name, bio = bio, website = website  ))
    }
}