package com.example.playlistmaker.di

import com.example.playlistmaker.favorites.view_model.FavoritesViewModel
import com.example.playlistmaker.player.view_model.PlayerViewModel
import com.example.playlistmaker.playlists.view_model.PlaylistsViewModel
import com.example.playlistmaker.playlists_creation.view_model.PlaylistsCreationViewModel
import com.example.playlistmaker.search.domain.model.Track
import com.example.playlistmaker.search.view_model.SearchViewModel
import com.example.playlistmaker.settings.view_model.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        SearchViewModel(get(), get())
    }

    viewModel { (track: Track) ->
        PlayerViewModel(track, get(), get(), get(), get())
    }

    viewModel {
        SettingsViewModel(get(), get())
    }

    viewModel {
        FavoritesViewModel(get())
    }

    viewModel {
        PlaylistsCreationViewModel(get(), get(), get())
    }

    viewModel {
        PlaylistsViewModel(get())
    }
}