package com.example.pictureoftheday.ui.main

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pictureoftheday.R
import com.example.pictureoftheday.SetTheme
import com.example.pictureoftheday.databinding.BottomSheetSettingsBinding
import com.example.pictureoftheday.domain.viewBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class BottomSheetSettingsFragment : Fragment(R.layout.bottom_sheet_settings) {

    private var setThemes: SetTheme? = null
    private var chipGreen: Chip? = null
    private var chipPink: Chip? = null

    private val B: BottomSheetSettingsBinding by viewBinding(
        BottomSheetSettingsBinding::bind
    )

    companion object {
        fun newInstance() = BottomSheetSettingsFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setThemes = context as SetTheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        B.apply {
            chipGreen.setOnClickListener {
                setThemes?.setTheme(1)
            }
            chipPink.setOnClickListener {
                setThemes?.setTheme(2)
            }
        }


    }

}