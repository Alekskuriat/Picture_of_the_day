package com.example.pictureoftheday.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.PictureOfTheDayFragmentBinding
import com.example.pictureoftheday.domain.appstate.PictureOfTheDayData
import com.example.pictureoftheday.domain.observe.PublisherHolder
import com.example.pictureoftheday.domain.router.AppRouter
import com.example.pictureoftheday.domain.router.RouterHolder
import com.example.pictureoftheday.domain.showSnackBar
import com.example.pictureoftheday.domain.toast
import com.example.pictureoftheday.domain.viewBinding
import com.google.android.material.chip.ChipGroup


class PictureOfTheDayFragment : Fragment(R.layout.picture_of_the_day_fragment) {

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }



    private var router : AppRouter? = null
    private var holder : PublisherHolder? = null
    private var dateSelect = 0;
    private var titleBottomSheetText: String = ""
    private var descriptionBottomSheetText: String = ""
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }


    private val B: PictureOfTheDayFragmentBinding by viewBinding(
        PictureOfTheDayFragmentBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity is RouterHolder) {
            router = (activity as RouterHolder?)!!.getRouter()
        }

        if (activity is PublisherHolder) {
            holder = activity as PublisherHolder?
        }

        B.apply {

            discriptionChip.setOnClickListener {
                holder?.getPublisher()?.showBottomSheet()
            }

            chipGroup.setOnCheckedChangeListener { _, checkedId ->
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
        viewModel.getData(dateSelect).observe(viewLifecycleOwner, Observer
        { renderData(it) })
    }


    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                B.apply {
                    progressBar.visibility = View.GONE
                    imageView.visibility = View.VISIBLE
                }

                titleBottomSheetText = data.serverResponseData.title.toString()
                descriptionBottomSheetText = data.serverResponseData.explanation.toString()

                holder?.getPublisher()?.notify(
                    titleBottomSheetText,
                    descriptionBottomSheetText
                )

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
