package com.example.playlistmaker.settings.domain.impl

import com.example.playlistmaker.settings.domain.SettingsRepository
import com.example.playlistmaker.settings.domain.models.ThemeSettings
import com.example.playlistmaker.settings.domain.SettingsInteractor

class SettingsInteractorImpl(private val settingsRepository: SettingsRepository): SettingsInteractor {

    override fun getThemeSettings(): ThemeSettings = settingsRepository.getThemeSettings()

    override fun updateThemeSetting(settings: ThemeSettings) {
        settingsRepository.updateThemeSetting(settings)
    }
}