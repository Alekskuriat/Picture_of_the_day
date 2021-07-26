package com.example.pictureoftheday.domain.viewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.pictureoftheday.ui.main.PictureDonkiFragment
import com.example.pictureoftheday.ui.main.PictureMarsFragment
import com.example.pictureoftheday.ui.main.PictureOfTheDayFragment

private const val POD_FRAGMENT = 0
private const val MARS_FRAGMENT = 1
private const val EARTH_FRAGMENT = 2

class ViewPagerAdapter(private val fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    private val fragments = arrayOf(
        PictureOfTheDayFragment(),
        PictureMarsFragment.newInstance(),
        PictureDonkiFragment.newInstance())

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> fragments[POD_FRAGMENT]
            1 -> fragments[MARS_FRAGMENT]
            2 -> fragments[EARTH_FRAGMENT]
            else -> fragments[EARTH_FRAGMENT]
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "POD"
            1 -> "Mars"
            2 -> "Earth"
            else -> "POD"
        }
    }



    override fun getCount(): Int {
        return fragments.size
    }
}

