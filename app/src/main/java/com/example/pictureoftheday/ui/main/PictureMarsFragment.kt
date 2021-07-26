package com.example.pictureoftheday.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.PictureMarsFragmentBinding
import com.example.pictureoftheday.databinding.PictureOfTheDayFragmentBinding
import com.example.pictureoftheday.domain.observe.PublisherHolder
import com.example.pictureoftheday.domain.router.AppRouter
import com.example.pictureoftheday.domain.viewBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.pictureoftheday.domain.appstate.MarsData
import com.example.pictureoftheday.domain.appstate.PictureOfTheDayData
import com.example.pictureoftheday.domain.showSnackBar
import com.example.pictureoftheday.domain.toast
import kotlin.random.Random

class PictureMarsFragment : Fragment(R.layout.picture_mars_fragment) {

    companion object {
        fun newInstance() = PictureMarsFragment()
    }

    private val viewModel: MarsViewModel by lazy {
        ViewModelProvider(this).get(MarsViewModel::class.java)
    }
    private val B: PictureMarsFragmentBinding by viewBinding(
        PictureMarsFragmentBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDataMars().observe(viewLifecycleOwner, Observer
        { renderData(it) })
    }

    private fun renderData(data: MarsData) {
        when (data) {
            is MarsData.Success -> {
                B.apply {
                    progressBar.visibility = View.GONE
                    imageView.visibility = View.VISIBLE
                }

                val serverResponseData = data.serverResponseData

                val item = serverResponseData.photos?.size.let {
                    (0..it!!).random()
                }
                B.cameraAbout.text = serverResponseData.photos?.get(item)?.camera?.fullName.toString()
                val url = serverResponseData.photos?.get(item)?.imgSrc
                if (url.isNullOrEmpty()) {
                    toast("Ссылка пустая")

                } else {
                    B.imageView.load(url) {
                        lifecycle(this@PictureMarsFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                }
            }
            is MarsData.Loading -> {
                B.apply {
                    imageView.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }

            }
            is MarsData.Error -> {
                B.imageView.showSnackBar(
                    "Ошибка",
                    "Повторить загрузку?"
                ) { viewModel.getDataMars() }
            }
        }

    }

}