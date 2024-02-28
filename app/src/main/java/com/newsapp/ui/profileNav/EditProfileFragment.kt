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

@Suppress("UNREACHABLE_CODE")
class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding
            .inflate(inflater, container, false
            )

        //changeKeyboard()
        textSetup()
        return binding.root

    }
    fun uploadImage(imgEditProfile : ImageView) {
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

    private fun changeKeyboard() {
        binding.etBio.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
        binding.etBio.imeOptions = EditorInfo.IME_ACTION_DONE

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
       val imgEditProfile = view.findViewById<ImageView>(R.id.imgEditProfile)
        binding.ivImageOpen.setOnClickListener {
            uploadImage(imgEditProfile)
        }
    }
}