package com.example.playlistmaker.di

import com.example.playlistmaker.favorites.view_model.FavoritesViewModel
import com.example.playlistmaker.playlists.view_model.PlaylistsViewModel
import com.example.playlistmaker.player.view_model.PlayerViewModel
import com.example.playlistmaker.playlist_details.view_model.PlaylistDetailsViewModel
import com.example.playlistmaker.playlist_edit.view_model.PlaylistEditViewModel
import com.example.playlistmaker.playlist_creation.view_model.PlaylistCreationViewModel
import com.example.playlistmaker.search.view_model.SearchViewModel
import com.example.playlistmaker.settings.view_model.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {

    viewModelOf(::SearchViewModel)
    viewModelOf(::PlayerViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::FavoritesViewModel)
    viewModelOf(::PlaylistCreationViewModel)
    viewModelOf(::PlaylistsViewModel)
    viewModelOf(::PlaylistDetailsViewModel)
    viewModelOf(::PlaylistEditViewModel)
}