package com.example.playlistmaker.settings.presentation.view_model

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.settings.domain.models.ThemeSettings
import com.example.playlistmaker.settings.domain.SettingsInteractor
import com.example.playlistmaker.sharing.domain.SharingInteractor

class SettingsViewModel(
    private val sharingInteractor: SharingInteractor,
    private val settingsInteractor: SettingsInteractor,
) : ViewModel() {

    private val darkThemeLiveData = MutableLiveData(settingsInteractor.getThemeSettings().isDarkTheme)
    fun observeDarkTheme(): LiveData<Boolean> = darkThemeLiveData

    fun onShareAppBtnClick() {
        sharingInteractor.shareApp()
    }

    fun onServiceBtnClick() {
        sharingInteractor.openSupport()
    }

    fun onTermsOfUseBtnClick() {
        sharingInteractor.openTermsOfUse()
    }

    fun switchTheme(isDarkTheme: Boolean) {
        if (darkThemeLiveData.value != isDarkTheme) {
            settingsInteractor.updateThemeSetting(ThemeSettings(isDarkTheme))
            darkThemeLiveData.value = isDarkTheme
            setAppDarkTheme(isDarkTheme)
        }
    }

    private fun setAppDarkTheme(isDarkTheme: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkTheme) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}