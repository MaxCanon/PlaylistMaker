package com.example.playlistmaker.di

import com.example.playlistmaker.utils.ResourceProvider
import com.example.playlistmaker.utils.ResourceProviderImpl
import com.markodevcic.peko.PermissionRequester
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val othersModule = module {

    singleOf(::ResourceProviderImpl) bind ResourceProvider::class

    single {
        PermissionRequester.instance()
    }
}