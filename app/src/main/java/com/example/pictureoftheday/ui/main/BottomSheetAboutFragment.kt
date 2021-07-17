package com.example.pictureoftheday.ui.main

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pictureoftheday.R

class BottomSheetAboutFragment : Fragment(R.layout.bottom_sheet_about) {

    private var titleBottomSheet: TextView? = null
    private var descriptionBottomSheet: TextView? = null

    companion object {
        fun newInstance(titleBottomSheet: String, descriptionBottomSheet: String): BottomSheetAboutFragment {
            val fragment = BottomSheetAboutFragment()
            val bundle = Bundle()
            bundle.putString("title", titleBottomSheet)
            bundle.putString("description", descriptionBottomSheet)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleBottomSheet = getView()?.findViewById<TextView>(R.id.bottom_sheet_description_header)
        descriptionBottomSheet = getView()?.findViewById<TextView>(R.id.bottom_sheet_description)

        titleBottomSheet?.text = arguments?.getString("title")
        descriptionBottomSheet?.text = arguments?.getString("description")

    }
}