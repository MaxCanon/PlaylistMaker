package com.example.playlistmaker

import android.app.Application
import com.example.playlistmaker.di.*
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.settings.domain.SettingsInteractor

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(dataModule, repositoryModule, interactorModule, viewModelModule)
        }
        val settingsInteractor : SettingsInteractor by inject()
        AppCompatDelegate.setDefaultNightMode(
            if (settingsInteractor.getThemeSettings().isDarkTheme) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}