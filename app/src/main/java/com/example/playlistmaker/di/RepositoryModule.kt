package com.example.playlistmaker.di

import com.example.playlistmaker.favorites.data.FavoritesRepositoryImpl
import com.example.playlistmaker.favorites.data.converters.TrackDbConverter
import com.example.playlistmaker.favorites.domain.api.FavoritesRepository
import com.example.playlistmaker.playlists_creation.data.converters.PlaylistDbConverter
import com.example.playlistmaker.playlists_creation.data.db.PlaylistsRepositoryImpl
import com.example.playlistmaker.playlists_creation.data.local_files.PlaylistsFilesRepositoryImpl
import com.example.playlistmaker.playlists_creation.domain.api.db.PlaylistsRepository
import com.example.playlistmaker.playlists_creation.domain.api.local_files.PlaylistsFilesRepository
import com.example.playlistmaker.search.data.api.SearchRepository
import com.example.playlistmaker.search.data.impl.SearchRepositoryImpl
import com.example.playlistmaker.settings.data.api.SettingsRepository
import com.example.playlistmaker.settings.data.impl.SettingsRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    single<SearchRepository> {
        SearchRepositoryImpl(get(), get(), get(), get())
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(get())
    }

    single { TrackDbConverter() }

    single<FavoritesRepository> {
        FavoritesRepositoryImpl(get(), get())
    }

    single { PlaylistDbConverter() }

    single<PlaylistsRepository> {
        PlaylistsRepositoryImpl(get(), get(), get())
    }

    single<PlaylistsFilesRepository> {
        PlaylistsFilesRepositoryImpl(get())
    }
}