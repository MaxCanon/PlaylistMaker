package com.example.playlistmaker.di

import com.example.playlistmaker.media.domain.models.Playlist
import com.example.playlistmaker.media.presentation.view_model.FavouritesViewModel
import com.example.playlistmaker.media.presentation.view_model.PlaylistDetailsViewModel
import com.example.playlistmaker.media.presentation.view_model.PlaylistEditViewModel
import com.example.playlistmaker.media.presentation.view_model.PlaylistViewModel
import com.example.playlistmaker.media.presentation.view_model.PlaylistsViewModel
import com.example.playlistmaker.player.presentation.view_model.PlayerViewModel
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.search.presentation.view_model.SearchViewModel
import com.example.playlistmaker.settings.presentation.view_model.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        SearchViewModel(get(), get())
    }

    viewModel {
        SettingsViewModel(get(), get())
    }

    viewModel { (track: Track) ->
        PlayerViewModel(track, get(), get(), get(), get())
    }

    viewModel {
        FavouritesViewModel(get())
    }

    viewModel {
        PlaylistsViewModel(get())
    }

    viewModel {
        PlaylistViewModel(get(), get())
    }

    viewModel { (playlistId: Long) ->
        PlaylistDetailsViewModel(playlistId, get(), get())
    }

    viewModel {(playlist: Playlist) ->
        PlaylistEditViewModel(playlist, get(), get())
    }
}