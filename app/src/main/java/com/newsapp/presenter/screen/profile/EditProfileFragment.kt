package com.newsapp.presenter.screen.profile

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.newsapp.data.models.Article
import com.newsapp.data.models.User
import com.newsapp.databinding.FragmentEditProfileBinding
import com.newsapp.presenter.viewmodel.ViewModelProfile
import com.newsapp.util.SharedPrefsManager

@Suppress("DEPRECATION")
class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private val viewModelProfile : ViewModelProfile by viewModels()
    private val prefs by lazy { SharedPrefsManager.getInstance(requireContext().applicationContext) }
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding
            .inflate(inflater, container, false
            )
        return binding.root
    }
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {

        imageUri = it
        if (imageUri != null) {
            viewModelProfile.updateUserProfile(imageUri!!) {
                dataOnEditText()
            }
        }
    }

    @SuppressLint("SetTextI18n")
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
        binding.includeEditFragment.etFillEmail.hint = "Aman Singh"
        binding.includeEditFragment.etFillPassWord.hint = "@aman_singh"

        /* Changing input type */
        binding.includeEditFragment.etFillEmail.inputType =
            InputType.TYPE_TEXT_VARIATION_PERSON_NAME

        binding.includeEditFragment.etFillPassWord.inputType =
            InputType.TYPE_TEXT_VARIATION_PERSON_NAME
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.includeBtn.btnAllInOne.text = "Save"
        binding.ivImageOpen.setOnClickListener {
            resultLauncher.launch("image/*")
        }

        binding.includeBtn.btnAllInOne.setOnClickListener {
            dataSave()
        }
        binding.imgBackArrow.setOnClickListener {
            findNavController().navigateUp()
        }

        textSetup()
        dataOnEditText()

    }
    private fun dataOnEditText() {
        val user = prefs.getUser()
        if (user?.profile != null && user.profile != "") {
            glideImage(user)
        }
        binding.includeEditFragment.etFillEmail.setText(user?.fullName)
        binding.includeEditFragment.etFillPassWord.setText(user?.userName)
        binding.includeBio.etBio.setText(user?.bio)
        binding.etWebsite.setText(user?.website)
    }

    private fun dataSave() {
        val fullName = binding.includeEditFragment.etFillEmail.text.toString()
        val name = binding.includeEditFragment.etFillPassWord.text.toString()
        val bio = binding.includeBio.etBio.text.toString()
        val website = binding.etWebsite.text.toString()

        if (fullName.isNotEmpty() && name.isNotEmpty()) {
            viewModelProfile.updateUserProfile(fullName, name, bio, website)
            findNavController().navigateUp()
        } else {
            Toast.makeText(requireContext(), "name and username must be fill",
                Toast.LENGTH_SHORT).show()
        }
    }
    private fun glideImage(user: User) {
        Glide.with(requireContext())
            .load(user.profile!!.toUri())
            .into(binding.imgEditProfile)
    }
}