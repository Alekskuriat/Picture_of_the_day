package com.example.pictureoftheday.domain.router

import androidx.fragment.app.FragmentManager
import com.example.pictureoftheday.R
import com.example.pictureoftheday.ui.main.PictureOfTheDayFragment

class AppRouter(
    private val fragmentManager: FragmentManager
){


    fun showStart() {
        fragmentManager.beginTransaction()
            .replace(R.id.container, PictureOfTheDayFragment.newInstance())
            .commitNow()
    }
}