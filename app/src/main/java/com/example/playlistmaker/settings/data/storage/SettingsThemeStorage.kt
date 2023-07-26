package com.example.playlistmaker.settings.data.storage

import com.example.playlistmaker.settings.domain.model.ThemeSettings

interface SettingsThemeStorage {
    fun saveThemeSettings(settings: ThemeSettings)
    fun getThemeSettings(): ThemeSettings
}