package com.example.pictureoftheday.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.MainFragmentBinding
import com.example.pictureoftheday.domain.observe.Publisher
import com.example.pictureoftheday.domain.observe.PublisherHolder
import com.example.pictureoftheday.domain.router.AppRouter
import com.example.pictureoftheday.domain.router.RouterHolder
import com.example.pictureoftheday.domain.viewBinding
import com.example.pictureoftheday.domain.viewPager.ViewPagerAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainFragment : Fragment(R.layout.main_fragment),
    com.example.pictureoftheday.domain.observe.Observer {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var publisher: Publisher? = null
    private var router: AppRouter? = null

    private val B: MainFragmentBinding by viewBinding(
        MainFragmentBinding::bind
    )
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    @SuppressLint("Range")
    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is PublisherHolder) {
            publisher = (activity as PublisherHolder?)!!.getPublisher()
            publisher?.addObserver(this);
        }
    }

    override fun onDetach() {
        publisher?.removeObserver(this);
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity is RouterHolder) {
            router = (activity as RouterHolder?)!!.getRouter()
        }

        B.apply {

            viewPager.adapter = ViewPagerAdapter(childFragmentManager)
            tabLayout.setupWithViewPager(viewPager)

        }
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
    }

    override fun update(titleBottomSheetText: String, descriptionBottomSheetText: String) {
            messageFromBottomSheet(titleBottomSheetText, descriptionBottomSheetText)
    }

    override fun showBottomSheet(bool: Boolean) {
        if (bool)  bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }


    private fun messageFromBottomSheet(titleBottomSheetText: String, descriptionBottomSheetText: String){
        childFragmentManager.beginTransaction()
            .replace(
                R.id.bottom_sheet_container_fragment,
                BottomSheetAboutFragment.newInstance(
                    titleBottomSheetText,
                    descriptionBottomSheetText
                )
            )
            .commitNow()
    }

}


