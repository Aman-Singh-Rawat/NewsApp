package com.newsapp.core.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.newsapp.R

open class BaseFragment : Fragment() {

    internal var progressDialog: Dialog? = null

    private val backPressedDispatcher = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPress()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, backPressedDispatcher)
    }

    open fun onBackPress(){
        Log.d(TAG, "onBackPress: ")
        findNavController().navigateUp()
    }

    internal fun showProgress() {
        if (progressDialog?.isShowing != true) {
            progressDialog = Dialog(requireActivity())
            progressDialog?.let {
                it.setContentView(R.layout.layout_progress_dialog)
                it.setCancelable(false)
                it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                it.window?.setLayout(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
                it.show()
            }
        }
    }

    internal fun hideProgress() {
        progressDialog?.cancel()
    }

    companion object {
        private const val TAG = "BaseFragment"
    }


}