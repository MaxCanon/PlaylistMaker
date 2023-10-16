package com.example.playlistmaker.di

import com.example.playlistmaker.favorites.domain.api.FavoritesInteractor
import com.example.playlistmaker.favorites.domain.impl.FavoritesInteractorImpl
import com.example.playlistmaker.player.domain.PlayerInteractorImpl
import com.example.playlistmaker.player.domain.api.PlayerInteractor
import com.example.playlistmaker.playlist_details.domain.api.PlaylistInteractor
import com.example.playlistmaker.playlist_details.domain.impl.PlaylistInteractorImpl
import com.example.playlistmaker.playlist_creation.domain.api.db.PlaylistsDbInteractor
import com.example.playlistmaker.playlist_creation.domain.api.local_files.PlaylistsFilesInteractor
import com.example.playlistmaker.playlist_creation.domain.impl.PlaylistsDbInteractorImpl
import com.example.playlistmaker.playlist_creation.domain.impl.PlaylistsFilesInteractorImpl
import com.example.playlistmaker.search.domain.api.SearchInteractor
import com.example.playlistmaker.search.domain.impl.SearchInteractorImpl
import com.example.playlistmaker.settings.domain.api.SettingsInteractorImpl
import com.example.playlistmaker.settings.domain.impl.SettingsInteractor
import com.example.playlistmaker.sharing.domain.api.SharingInteractor
import com.example.playlistmaker.sharing.domain.impl.SharingInteractorImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val interactorModule = module {

    singleOf(::PlayerInteractorImpl) bind PlayerInteractor::class
    singleOf(::SearchInteractorImpl) bind SearchInteractor::class
    singleOf(::SharingInteractorImpl) bind SharingInteractor::class
    singleOf(::SettingsInteractorImpl) bind SettingsInteractor::class
    singleOf(::FavoritesInteractorImpl) bind FavoritesInteractor::class
    singleOf(::PlaylistsDbInteractorImpl) bind PlaylistsDbInteractor::class
    singleOf(::PlaylistsFilesInteractorImpl) bind PlaylistsFilesInteractor::class
    singleOf(::PlaylistInteractorImpl) bind PlaylistInteractor::class
}