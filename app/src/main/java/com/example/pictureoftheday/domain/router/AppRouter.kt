package com.example.pictureoftheday.domain.router

import androidx.fragment.app.FragmentManager
import com.example.pictureoftheday.R
import com.example.pictureoftheday.ui.main.*

class AppRouter(
    private val fragmentManager: FragmentManager
){


    fun showStart() {
        fragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commitNow()
    }

    fun showSettings(childFragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
            .replace(
                R.id.bottom_sheet_container_fragment,
                BottomSheetSettingsFragment.newInstance()
            )
            .commitNow()
    }

    fun showViewPager() {
        fragmentManager.beginTransaction()
            .replace(R.id.container, ViewPagerFragment.newInstance())
            .commitNow()
    }

    fun showToDoList() {
        fragmentManager.beginTransaction()
            .replace(R.id.container, ToDoListFragment.newInstance())
    //        .addToBackStack("ToDoList")
            .commitNow()
    }
}