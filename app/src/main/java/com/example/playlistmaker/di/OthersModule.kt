package com.example.playlistmaker.di

import com.example.playlistmaker.utils.ResourceProvider
import com.example.playlistmaker.utils.ResourceProviderImpl
import com.markodevcic.peko.PermissionRequester
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val othersModule = module {

    single<ResourceProvider> {
        ResourceProviderImpl(androidContext())
    }

    single {
        PermissionRequester.instance()
    }
}