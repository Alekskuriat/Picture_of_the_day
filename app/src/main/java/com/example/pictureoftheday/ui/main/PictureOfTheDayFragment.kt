package com.example.pictureoftheday.ui.main

import android.os.Bundle
import android.system.Os.bind
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import coil.api.load
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.PictureOfTheDayFragmentBinding
import com.example.pictureoftheday.databinding.PictureOfTheDayFragmentStartBinding
import com.example.pictureoftheday.domain.appstate.PictureOfTheDayData
import com.example.pictureoftheday.domain.observe.PublisherHolder
import com.example.pictureoftheday.domain.router.AppRouter
import com.example.pictureoftheday.domain.router.RouterHolder
import com.example.pictureoftheday.domain.showSnackBar
import com.example.pictureoftheday.domain.toast
import com.example.pictureoftheday.domain.viewBinding


class PictureOfTheDayFragment : Fragment(R.layout.picture_of_the_day_fragment_start) {

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }


    private var router: AppRouter? = null
    private var holder: PublisherHolder? = null
    private var dateSelect = 0;
    private var show = false
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }


    private val B: PictureOfTheDayFragmentStartBinding by viewBinding(
        PictureOfTheDayFragmentStartBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity is RouterHolder) {
            router = (activity as RouterHolder?)!!.getRouter()
        }

        if (activity is PublisherHolder) {
            holder = activity as PublisherHolder?
        }

        B.imageView.setOnClickListener {
            if (show) hideComponents() else showComponents()
        }

        viewModel.getDataPod(dateSelect).observe(viewLifecycleOwner, Observer
        { renderData(it) })
    }


    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                B.apply {
                    progressBar.visibility = View.GONE
                    tapToScreen.visibility = View.VISIBLE
                    titlePictureDay.visibility = View.VISIBLE
                    description.visibility = View.VISIBLE
                    title.text = data.serverResponseData.title.toString()
                    description.text = data.serverResponseData.explanation.toString()
                }

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
                    progressBar.visibility = View.VISIBLE
                    tapToScreen.visibility = View.GONE
                    titlePictureDay.visibility = View.GONE
                    description.visibility = View.GONE
                }

            }
            is PictureOfTheDayData.Error -> {
                B.main.showSnackBar(
                    "Ошибка",
                    "Повторить загрузку?"
                ) { viewModel.getDataPod(dateSelect) }
            }
        }
    }

    private fun showComponents() {

        ConstraintSet().apply {
            clone(context, R.layout.picture_of_the_day_fragment)
            ChangeBounds().apply {
                interpolator = AnticipateOvershootInterpolator(2.0f)
                duration = 1200
                TransitionManager.beginDelayedTransition(B.main, this)
            }
            applyTo(B.main)
            show = true
        }
    }

    private fun hideComponents() {

        ConstraintSet().apply {
            clone(context, R.layout.picture_of_the_day_fragment_start)
            ChangeBounds().apply {
                interpolator = AnticipateOvershootInterpolator(0.5f)
                duration = 600
                TransitionManager.beginDelayedTransition(B.main, this)
            }
            applyTo(B.main)
            show = false
        }

    }
}
