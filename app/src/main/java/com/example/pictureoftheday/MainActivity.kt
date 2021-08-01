package com.example.pictureoftheday

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.pictureoftheday.domain.observe.Publisher
import com.example.pictureoftheday.domain.observe.PublisherHolder
import com.example.pictureoftheday.domain.router.AppRouter
import com.example.pictureoftheday.domain.router.RouterHolder
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationBarView

interface SetTheme {
    fun setTheme(id: Int?)
}

const val SET_THEME = "set_theme"

class MainActivity : AppCompatActivity(), SetTheme, RouterHolder, PublisherHolder {


    private val publisher = Publisher()
    private var theme: Int? = null
    private lateinit var router: AppRouter
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        router = AppRouter(supportFragmentManager)

        val sharedPreferences: SharedPreferences = getSharedPreferences("SP", MODE_PRIVATE)


        if (savedInstanceState == null) {
            theme = sharedPreferences.getInt(SET_THEME, 1)
        } else {
            theme = savedInstanceState.getInt(SET_THEME)
        }

        when (theme) {
            1 -> setTheme(R.style.AppThemePOD_GreenTheme)
            2 -> setTheme(R.style.AppThemePOD_PinkTheme)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        setBottomSheetBehavior(findViewById(R.id.bottom_sheet_container))
        if (savedInstanceState == null) {
            router.showToDoList()
        }

        bottomNavigation.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_one -> {
                    router.showStart()
                    true
                }
                R.id.navigation_two -> {
                    router.showSettings(supportFragmentManager)
                    true
                }
                R.id.navigation_three -> {
                    router.showViewPager()

                    true
                }
                R.id.navigation_four -> {
                    router.showToDoList()
                    true
                }
                else -> false
            }

        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        theme?.let { outState.putInt(SET_THEME, it) }
        
    }


    override fun setTheme(id: Int?) {
        val ed: SharedPreferences.Editor? = getSharedPreferences("SP", MODE_PRIVATE).edit()
        id?.let {
            theme = id
            ed?.putInt(SET_THEME, it)
        }
        ed?.apply()
        recreate()
    }

    override fun getRouter(): AppRouter? {
        return router
    }

    override fun getPublisher(): Publisher? {
        return publisher
    }
}