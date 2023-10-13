package com.example.playlistmaker

import android.app.Application
import com.example.playlistmaker.di.*
import com.example.playlistmaker.settings.data.api.SettingsRepository
import com.markodevcic.peko.PermissionRequester
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    private val repository: SettingsRepository by inject()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                repositoryModule,
                dataModule,
                interactorModule,
                othersModule,
                navigatorModule,
                viewModelModule,
            )
        }
        repository.applyAppTheme()
        PermissionRequester.initialize(applicationContext)
    }
}