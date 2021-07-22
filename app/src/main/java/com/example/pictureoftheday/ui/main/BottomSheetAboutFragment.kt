package com.example.pictureoftheday.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pictureoftheday.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class BottomSheetAboutFragment : Fragment(R.layout.bottom_sheet_about) {

    private var titleBottomSheet: TextView? = null
    private var descriptionBottomSheet: TextView? = null
    private var inputLayout: TextInputLayout? = null
    private var inputEditText: TextInputEditText? = null


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
        titleBottomSheet = getView()?.findViewById(R.id.bottom_sheet_description_header)
        descriptionBottomSheet = getView()?.findViewById(R.id.bottom_sheet_description)
        inputLayout = getView()?.findViewById(R.id.input_layout)
        inputEditText = getView()?.findViewById(R.id.input_edit_text)


        titleBottomSheet?.text = arguments?.getString("title")
        descriptionBottomSheet?.text = arguments?.getString("description")

        inputLayout?.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://ru.wikipedia.org/wiki/${inputEditText?.text.toString()}")
            })
        }

    }
}