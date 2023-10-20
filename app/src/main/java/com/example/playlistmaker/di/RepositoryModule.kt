package com.example.playlistmaker.di

import com.example.playlistmaker.media.data.impl.FavouritesRepositoryImpl
import com.example.playlistmaker.media.data.impl.PlaylistRepositoryImpl
import com.example.playlistmaker.media.data.mapper.PlaylistDbMapper
import com.example.playlistmaker.media.data.mapper.PlaylistTrackDbMapper
import com.example.playlistmaker.media.data.mapper.TrackDbMapper
import com.example.playlistmaker.media.domain.api.FavouritesRepository
import com.example.playlistmaker.media.domain.api.PlaylistRepository
import com.example.playlistmaker.player.data.MediaPlayerRepositoryImpl
import com.example.playlistmaker.player.domain.api.MediaPlayerRepository
import com.example.playlistmaker.search.data.impl.HistoryRepositoryImpl
import com.example.playlistmaker.search.data.impl.TrackRepositoryImpl
import com.example.playlistmaker.search.domain.api.HistoryRepository
import com.example.playlistmaker.search.domain.api.TrackRepository
import com.example.playlistmaker.settings.data.impl.SettingsRepositoryImpl
import com.example.playlistmaker.settings.domain.SettingsRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<MediaPlayerRepository> {
        MediaPlayerRepositoryImpl(get())
    }

    single<HistoryRepository> {
        HistoryRepositoryImpl(get(), get())
    }

    single<TrackRepository> {
        TrackRepositoryImpl(get(), get())
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(get())
    }

    single<FavouritesRepository> {
        FavouritesRepositoryImpl(get(), get())
    }

    single<PlaylistRepository> {
        PlaylistRepositoryImpl(get(), get(), get())
    }

    factory {
        TrackDbMapper()
    }

    factory {
        PlaylistDbMapper(get())
    }

    factory {
        PlaylistTrackDbMapper()
    }
}