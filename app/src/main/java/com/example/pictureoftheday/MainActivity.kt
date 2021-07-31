package com.example.pictureoftheday

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pictureoftheday.domain.observe.Publisher
import com.example.pictureoftheday.domain.observe.PublisherHolder
import com.example.pictureoftheday.domain.router.AppRouter
import com.example.pictureoftheday.domain.router.RouterHolder

interface SetTheme {
    fun setTheme(id: Int?)
}

const val SET_THEME = "set_theme"

class MainActivity : AppCompatActivity(), SetTheme, RouterHolder, PublisherHolder {


    private val publisher = Publisher()
    private var theme: Int? = null
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