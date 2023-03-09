package com.example.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val toolbar = findViewById<Toolbar>(R.id.settings_toolbar)

        toolbar.setNavigationOnClickListener {
            finish()
        }


        /*val backToMainMenu = findViewById<Toolbar>(R.id.icon_arrow_back)

        backToMainMenu.setOnClickListener {
            finish()
        } */
    }
}