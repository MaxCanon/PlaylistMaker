package com.example.playlistmaker

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.divider.MaterialDivider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        val divider = findViewById<MaterialDivider>(R.id.divider)
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.mediaFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                    divider.visibility = View.VISIBLE
                }
                R.id.favoritesFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                    divider.visibility = View.VISIBLE
                }
                R.id.playlistsFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                    divider.visibility = View.VISIBLE
                }
                R.id.settingsFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                    divider.visibility = View.VISIBLE
                }
                R.id.searchFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                    divider.visibility = View.VISIBLE
                }
                R.id.playlistCreationFragment -> {
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    bottomNavigationView.visibility = View.GONE
                    divider.visibility = View.GONE

                }
                R.id.playerFragment -> {
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    bottomNavigationView.visibility = View.GONE
                    divider.visibility = View.GONE
                }
                else -> {
                    bottomNavigationView.visibility = View.GONE
                    divider.visibility = View.GONE
                }
            }
        }
    }
}