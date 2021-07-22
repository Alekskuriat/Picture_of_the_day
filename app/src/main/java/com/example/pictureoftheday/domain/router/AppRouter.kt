package com.example.pictureoftheday.domain.router

import androidx.fragment.app.FragmentManager
import com.example.pictureoftheday.R
import com.example.pictureoftheday.ui.main.BottomSheetSettingsFragment
import com.example.pictureoftheday.ui.main.MainFragment
import com.example.pictureoftheday.ui.main.PictureOfTheDayFragment
import com.example.pictureoftheday.ui.main.ViewPagerFragment

class AppRouter(
    private val fragmentManager: FragmentManager
){


    fun showStart() {
        fragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commitNow()
    }

    fun showSettings(childFragmentManager: FragmentManager) {
        childFragmentManager.beginTransaction()
            .replace(
                R.id.bottom_sheet_container_fragment,
                BottomSheetSettingsFragment.newInstance()
            )
            .commitNow()
    }

    fun showViewPager(childFragmentManager: FragmentManager) {
        childFragmentManager.beginTransaction()
            .replace(R.id.container, ViewPagerFragment.newInstance())
            .commitNow()
    }
}