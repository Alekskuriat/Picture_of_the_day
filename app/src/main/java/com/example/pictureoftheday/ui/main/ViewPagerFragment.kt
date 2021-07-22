package com.example.pictureoftheday.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.PictureOfTheDayFragmentBinding
import com.example.pictureoftheday.databinding.ViewPagerFragmentBinding
import com.example.pictureoftheday.domain.viewBinding
import com.example.pictureoftheday.domain.viewPager.ViewPagerAdapter

class ViewPagerFragment : Fragment(R.layout.view_pager_fragment) {

    companion object {
        fun newInstance() = ViewPagerFragment()
    }

    private val B: ViewPagerFragmentBinding by viewBinding(
        ViewPagerFragmentBinding::bind
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        B.viewPager.adapter = ViewPagerAdapter(childFragmentManager)
    }

}