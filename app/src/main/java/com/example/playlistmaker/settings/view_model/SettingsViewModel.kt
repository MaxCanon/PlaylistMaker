package com.example.playlistmaker.settings.view_model

import androidx.lifecycle.ViewModel
import com.example.playlistmaker.settings.domain.ThemeSettings
import com.example.playlistmaker.settings.domain.impl.SettingsInteractor
import com.example.playlistmaker.sharing.domain.api.SharingInteractor

class SettingsViewModel(
    private val sharingInteractor: SharingInteractor,
    private val settingsInteractor: SettingsInteractor,
) : ViewModel() {

    fun onShareAppPressed() {
        sharingInteractor.shareApp()
    }

    fun onSupportPressed() {
        sharingInteractor.openSupport()
    }

    fun onUserAgreementPressed() {
        sharingInteractor.openTerms()
    }

    fun onThemeSwitched(isChecked: Boolean) {
        settingsInteractor.updateThemeSetting(ThemeSettings(isChecked))
    }

    fun darkThemeIsEnabled() = settingsInteractor.getThemeSettings().darkThemeEnabled
}