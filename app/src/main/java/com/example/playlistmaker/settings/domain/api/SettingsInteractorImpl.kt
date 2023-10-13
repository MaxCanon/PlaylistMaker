package com.example.playlistmaker.settings.domain.api

import com.example.playlistmaker.settings.data.api.SettingsRepository
import com.example.playlistmaker.settings.domain.ThemeSettings
import com.example.playlistmaker.settings.domain.impl.SettingsInteractor

class SettingsInteractorImpl(private val settingsRepository: SettingsRepository) :
    SettingsInteractor {
    override fun getThemeSettings() = settingsRepository.getThemeSettings()

    override fun updateThemeSetting(settings: ThemeSettings) {
        settingsRepository.updateThemeSetting(settings)
    }
}