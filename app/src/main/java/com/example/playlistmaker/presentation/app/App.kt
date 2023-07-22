package com.example.playlistmaker.presentation.app

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import com.example.playlistmaker.data.dto.TrackDto
import com.google.gson.Gson

class App : Application() {

    var darkTheme = false
    var currentMusicTrack: TrackDto? = null

    companion object {
        lateinit var sharedMemory: SharedPreferences
        const val PREFERENCES = "preferences"
        const val KEY_THEME = "key"
        const val CURRENT_PLAYING_TRACK = "key_for_saving_current_track"
        const val TRACK = "track"
        var themeDark = false

    }

    override fun onCreate() {
        super.onCreate()
        sharedMemory = getSharedPreferences(PREFERENCES, MODE_PRIVATE)
        darkTheme = sharedMemory.getBoolean(KEY_THEME, false)
        themeDark = darkTheme
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

    fun saveCurrentPlayingTrack(trackToSafe: TrackDto) {
        currentMusicTrack = trackToSafe
        val jsonTrack = Gson().toJson(trackToSafe, TrackDto::class.java)
        sharedMemory.edit().putString(CURRENT_PLAYING_TRACK, jsonTrack).apply()
    }

    private fun loadCurrentPlayingTrack(): TrackDto? {
        val jsonTrack = sharedMemory.getString(CURRENT_PLAYING_TRACK, "")
        return if (!jsonTrack.isNullOrEmpty()) {
            Gson().fromJson(jsonTrack, TrackDto::class.java)
        } else null
    }
}