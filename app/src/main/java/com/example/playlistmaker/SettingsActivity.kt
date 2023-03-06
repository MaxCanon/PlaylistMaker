package com.example.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backToMainMenu = findViewById<ImageView>(R.id.icon_arrow_back)

        backToMainMenu.setOnClickListener {
            val backToMainMenuIntent = Intent(this, MainActivity::class.java)
            startActivity(backToMainMenuIntent)
        }
    }
}