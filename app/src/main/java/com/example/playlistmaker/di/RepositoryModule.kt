package com.example.playlistmaker.di

import com.example.playlistmaker.favorites.data.FavoritesRepositoryImpl
import com.example.playlistmaker.favorites.data.converters.TrackDbConverter
import com.example.playlistmaker.favorites.domain.api.FavoritesRepository
import com.example.playlistmaker.playlist_details.data.PlaylistRepositoryImpl
import com.example.playlistmaker.playlist_details.domain.api.PlaylistRepository
import com.example.playlistmaker.playlist_creation.data.converters.PlaylistDbConverter
import com.example.playlistmaker.playlist_creation.data.db.PlaylistsRepositoryImpl
import com.example.playlistmaker.playlist_creation.data.local_files.PlaylistsFilesRepositoryImpl
import com.example.playlistmaker.playlist_creation.domain.api.db.PlaylistsRepository
import com.example.playlistmaker.playlist_creation.domain.api.local_files.PlaylistsFilesRepository
import com.example.playlistmaker.search.data.api.SearchRepository
import com.example.playlistmaker.search.data.impl.SearchRepositoryImpl
import com.example.playlistmaker.settings.data.api.SettingsRepository
import com.example.playlistmaker.settings.data.impl.SettingsRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {

    singleOf(::SearchRepositoryImpl) bind SearchRepository::class
    singleOf(::SettingsRepositoryImpl) bind SettingsRepository::class
    singleOf(::TrackDbConverter)
    singleOf(::FavoritesRepositoryImpl) bind FavoritesRepository::class
    singleOf(::PlaylistDbConverter)
    singleOf(::PlaylistsRepositoryImpl) bind PlaylistsRepository::class
    singleOf(::PlaylistsFilesRepositoryImpl) bind PlaylistsFilesRepository::class
    singleOf(::PlaylistRepositoryImpl) bind PlaylistRepository::class
}