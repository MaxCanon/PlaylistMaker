package com.example.playlistmaker.main

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.playlistmaker.R
import com.example.playlistmaker.search.ui.activity.SearchActivity
import com.example.playlistmaker.media.activity.MediaActivity
import com.example.playlistmaker.settings.ui.activity.SettingsActivity

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
        imageLibrary.setOnClickListener { navigateTo(MediaActivity::class.java) }
        imageSettings.setOnClickListener { navigateTo(SettingsActivity::class.java) }

    }
}

