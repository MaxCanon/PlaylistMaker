package com.example.playlistmaker.di

import com.example.playlistmaker.sharing.data.ExternalNavigator
import com.example.playlistmaker.sharing.data.impl.ExternalNavigatorImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val navigatorModule = module {

    singleOf(::ExternalNavigatorImpl) bind ExternalNavigator::class
}