package com.dashkevich.bottom

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomFragment : Fragment(R.layout.fragment_bottom) {

    lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHost =
            childFragmentManager.findFragmentById(R.id.bottom_container) as NavHostFragment
        navController = navHost.navController
        navController.setGraph(com.dashkevich.navigation.R.navigation.bottom_nav_graph)
        val bottomBar = view.findViewById<BottomNavigationView>(R.id.bottom_bar)

        bottomBar.setupWithNavController(navController)

    }
}