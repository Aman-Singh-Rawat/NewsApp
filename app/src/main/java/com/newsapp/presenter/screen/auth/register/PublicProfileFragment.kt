package com.newsapp.presenter.screen.auth.register

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.newsapp.R
import com.newsapp.databinding.FragmentProfilePublicBinding
import com.newsapp.presenter.viewmodel.ViewModelProfile
import com.newsapp.util.SharedPrefsManager

class PublicProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfilePublicBinding
    private val viewModel : ViewModelProfile by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfilePublicBinding.inflate(
            inflater, container, false
        )
        return binding.root

    }

    @SuppressLint("SetTextI18n")
    private fun changingValue() {
        binding.includeProfile.tvEmail.text = "Full Name"
        binding.includeProfile.tvPassword.text = "Username"
        binding.includeProfile.etFillEmail.hint = "Aman Singh"
        binding.includeProfile.etFillPassWord.hint = "@aman_singh"
        binding.includeBio.etBio.hint = "Tech enthusiast, likes to share stories a..."
        binding.includeBio.tvBio.text = "Bio"

        binding.includeBio.etBio.setCompoundDrawablesRelativeWithIntrinsicBounds(
            null, null, null, null)
        binding.includeBio.etBio.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
        binding.includeProfile.tvEmail.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
        binding.includeProfile.tvPassword.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
        binding.includeProfile.etFillEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(
            null, null, null, null)
        binding.includeProfile.etFillPassWord.setCompoundDrawablesRelativeWithIntrinsicBounds(
            null, null, null, null)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.includeProfileFragment.btnAllInOne.text = "Finish"

        binding.includeProfileFragment.btnAllInOne.setOnClickListener {
            dataSave()
        }
        binding.ivBackArrowProfile.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.ivProfile.setOnClickListener {
            uploadImage(binding.ivProfile)
        }

        changingValue()


    }
    private fun dataSave() {
        val fullName = binding.includeProfile.etFillEmail.text.toString()
        val name = binding.includeProfile.etFillPassWord.text.toString()
        val bio = binding.includeBio.etBio.text.toString()
        val website = binding.etWebsite.text.toString()

        if(fullName.isNotEmpty() && name.isNotEmpty()) {
            viewModel.updateUserProfile(fullName, name, bio, website)
            findNavController().navigate(R.id.all_Set_Fragment)
        } else {
            Toast.makeText(requireActivity(), "Please fill the userName or name", Toast.LENGTH_SHORT).show()
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


