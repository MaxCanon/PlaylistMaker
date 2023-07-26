package com.example.playlistmaker.settings.domain.impl

import com.example.playlistmaker.settings.domain.interactor.SettingsInteractor
import com.example.playlistmaker.settings.domain.model.ThemeSettings
import com.example.playlistmaker.settings.domain.repository.SettingsRepository

class SettingsInteractorImpl(private val repository: SettingsRepository): SettingsInteractor {
    override fun getThemeSettings(): ThemeSettings {
        return repository.getThemeSettings()
    }

    override fun updateThemeSetting(settings: ThemeSettings) {
        repository.updateThemeSetting(settings)
    }
}