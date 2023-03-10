package com.example.playlistmaker

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

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

