package com.example.playlistmaker.presentation.main

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.playlistmaker.R
import com.example.playlistmaker.presentation.search.SearchActivity
import com.example.playlistmaker.presentation.library.LibraryActivity
import com.example.playlistmaker.presentation.settings.SettingsActivity

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageSearch = findViewById<Button>(R.id.icon_search)
        val imageLibrary = findViewById<Button>(R.id.icon_media_library)
        val imageSettings = findViewById<Button>(R.id.icon_settings)

        fun navigateTo(clazz: Class<out AppCompatActivity>) {
            val intent = Intent(this, clazz)
            startActivity(intent)
        }
        imageSearch.setOnClickListener { navigateTo(SearchActivity::class.java) }
        imageLibrary.setOnClickListener { navigateTo(LibraryActivity::class.java) }
        imageSettings.setOnClickListener { navigateTo(SettingsActivity::class.java) }

    }
}

