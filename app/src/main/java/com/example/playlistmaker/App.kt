package com.example.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit

class App : Application() {

    var darkTheme = false

    companion object {
        lateinit var sharedMemory: SharedPreferences
        const val PREFERENCES = "preferences"
        const val KEY_THEME = "key"
        const val TRACK = "track"
    }

    override fun onCreate() {
        super.onCreate()
        sharedMemory = getSharedPreferences(PREFERENCES, MODE_PRIVATE)
        darkTheme = sharedMemory.getBoolean(KEY_THEME, false)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
        sharedMemory.edit {
            putBoolean(KEY_THEME, darkTheme)
        }
    }
}