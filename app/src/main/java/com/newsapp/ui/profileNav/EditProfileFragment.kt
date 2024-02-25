package com.newsapp.ui.profileNav

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.databinding.FragmentEditProfileBinding

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
}