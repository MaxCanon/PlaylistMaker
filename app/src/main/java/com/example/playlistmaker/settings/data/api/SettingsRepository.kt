package com.example.playlistmaker.settings.data.api

import com.example.playlistmaker.settings.domain.ThemeSettings

interface SettingsRepository {
    fun getThemeSettings(): ThemeSettings
    fun updateThemeSetting(settings: ThemeSettings)
    fun applyAppTheme()
}