package com.example.pictureoftheday.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.PictureOfTheDayFragmentBinding
import com.example.pictureoftheday.domain.appstate.PictureOfTheDayData
import com.example.pictureoftheday.domain.showSnackBar
import com.example.pictureoftheday.domain.toast
import com.example.pictureoftheday.domain.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior


class PictureOfTheDayFragment : Fragment(R.layout.picture_of_the_day_fragment) {

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }

    private var dateSelect = 0;
    private var titleBottomSheet: TextView? = null
    private var descriptionBottomSheet: TextView? = null
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private val B: PictureOfTheDayFragmentBinding by viewBinding(
        PictureOfTheDayFragmentBinding::bind
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleBottomSheet = getView()?.findViewById<TextView>(R.id.bottom_sheet_description_header)
        descriptionBottomSheet = getView()?.findViewById<TextView>(R.id.bottom_sheet_description)

        B.apply {
            todayChip.isChecked = true

            discriptionChip.setOnClickListener {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
            }

            inputLayout.setEndIconOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data =
                        Uri.parse("https://ru.wikipedia.org/wiki/${B.inputEditText.text.toString()}")
                })
            }

            chipGroup.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    1 -> {
                        dateSelect = -2
                        B.titlePictureDay.text = getString(R.string.picture_before_yesterday)
                    }
                    2 -> {
                        dateSelect = -1
                        B.titlePictureDay.text = getString(R.string.picture_yesterday)
                    }
                    3 -> {
                        dateSelect = 0
                        B.titlePictureDay.text = getString(R.string.picture_today)
                    }
                    else -> {
                        dateSelect = 0
                        B.titlePictureDay.text = getString(R.string.picture_today)
                    }
                }
                viewModel.getData(dateSelect)
            }

        }

        viewModel.getData(dateSelect).observe(viewLifecycleOwner, Observer { renderData(it) })

        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))

    }


    @SuppressLint("Range")
    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                B.apply {
                    progressBar.visibility = View.GONE
                    imageView.visibility = View.VISIBLE
                }

                titleBottomSheet?.text = data.serverResponseData.title
                descriptionBottomSheet?.text = data.serverResponseData.explanation

                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    toast("Ссылка пустая")

                } else {
                    B.imageView.load(url) {
                        lifecycle(this@PictureOfTheDayFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                }
            }
            is PictureOfTheDayData.Loading -> {
                B.apply {
                    imageView.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }

            }
            is PictureOfTheDayData.Error -> {
                B.main.showSnackBar(
                    "Ошибка",
                    "Повторить загрузку?"
                ) { viewModel.getData(dateSelect) }
            }
        }
    }
}
