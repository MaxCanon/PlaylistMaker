package com.example.playlistmaker.di

import com.example.playlistmaker.media.viewmodel.FavoriteTracksViewModel
import com.example.playlistmaker.media.viewmodel.PlayListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mediaModule = module {

    viewModel{
        FavoriteTracksViewModel()
    }

    viewModel{
        PlayListViewModel()
    }
}