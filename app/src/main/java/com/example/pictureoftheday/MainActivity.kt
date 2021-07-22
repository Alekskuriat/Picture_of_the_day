package com.example.pictureoftheday

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pictureoftheday.domain.router.AppRouter
import com.example.pictureoftheday.domain.router.RouterHolder
import com.example.pictureoftheday.ui.main.PictureOfTheDayFragment

interface SetTheme {
    fun setTheme(id: Int?)
}

const val SET_THEME = "set_theme"

class MainActivity : AppCompatActivity(), SetTheme, RouterHolder {

    private var themeId: Int? = null
    private var theme: Int? = null
    private var sharedPreferences: SharedPreferences? = null
    private lateinit var router: AppRouter

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
        if (savedInstanceState == null) {
            router.showStart()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        themeId?.let { outState.putInt(SET_THEME, it) }
    }


    override fun setTheme(id: Int?) {
        val ed: SharedPreferences.Editor? = getSharedPreferences("SP", MODE_PRIVATE).edit()
        id?.let {
            themeId = id
            ed?.putInt(SET_THEME, it)
        }
        ed?.apply()
        recreate()
    }

    override fun getRouter(): AppRouter? {
        return router
    }
}