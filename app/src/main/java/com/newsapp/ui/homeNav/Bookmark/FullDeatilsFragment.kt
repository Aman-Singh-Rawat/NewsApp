package com.newsapp.ui.homeNav.Bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.newsapp.R

class FullDeatilsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the main fragment layout
        return inflater.inflate(R.layout.fragment_full_details, container, false)
    }
    //Todo

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ivBookMark = view.findViewById<ImageView>(R.id.ivBookMark)
        ivBookMark.setOnClickListener {

            val bottomSheetView = layoutInflater.inflate(R.layout.fragment_book_mark_bottom_sheet, null)
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }
    }
}
