package com.dashkevich.dish

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = (supportFragmentManager.findFragmentById(R.id.root_container) as NavHostFragment)
                .navController
        navController.setGraph(com.dashkevich.navigation.R.navigation.root_nav_graph)
        configSystemUI()
    }

    private fun configSystemUI() {
        val wic = WindowInsetsControllerCompat(window, window.decorView)

        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                wic.isAppearanceLightStatusBars = true
                wic.isAppearanceLightNavigationBars = true
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                wic.isAppearanceLightStatusBars = true
                wic.isAppearanceLightNavigationBars = true
            }
        }
    }
}