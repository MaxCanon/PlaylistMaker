package com.example.playlistmaker.di

import com.example.playlistmaker.favorites.domain.api.FavoritesInteractor
import com.example.playlistmaker.favorites.domain.impl.FavoritesInteractorImpl
import com.example.playlistmaker.player.domain.PlayerInteractorImpl
import com.example.playlistmaker.player.domain.api.PlayerInteractor
import com.example.playlistmaker.playlists_creation.domain.api.db.PlaylistsDbInteractor
import com.example.playlistmaker.playlists_creation.domain.api.local_files.PlaylistsFilesInteractor
import com.example.playlistmaker.playlists_creation.domain.impl.PlaylistsDbInteractorImpl
import com.example.playlistmaker.playlists_creation.domain.impl.PlaylistsFilesInteractorImpl
import com.example.playlistmaker.search.domain.api.SearchInteractor
import com.example.playlistmaker.search.domain.impl.SearchInteractorImpl
import com.example.playlistmaker.settings.domain.api.SettingsInteractorImpl
import com.example.playlistmaker.settings.domain.impl.SettingsInteractor
import com.example.playlistmaker.sharing.domain.api.SharingInteractor
import com.example.playlistmaker.sharing.domain.impl.SharingInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    factory<PlayerInteractor> {
        PlayerInteractorImpl(get())
    }

    single<SearchInteractor> {
        SearchInteractorImpl(get())
    }

    single<SharingInteractor> {
        SharingInteractorImpl(get(), get())
    }

    single<SettingsInteractor> {
        SettingsInteractorImpl(get())
    }

    single<FavoritesInteractor> {
        FavoritesInteractorImpl(get())
    }

    single<PlaylistsDbInteractor> {
        PlaylistsDbInteractorImpl(get())
    }

    single<PlaylistsFilesInteractor> {
        PlaylistsFilesInteractorImpl(get())
    }
}