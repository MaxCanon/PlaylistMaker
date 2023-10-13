package com.example.playlistmaker.settings.domain.impl

import com.example.playlistmaker.settings.domain.ThemeSettings

interface SettingsInteractor {
    fun getThemeSettings(): ThemeSettings
    fun updateThemeSetting(settings: ThemeSettings)
}