package com.example.playlistmaker.media.presentation.models

import com.example.playlistmaker.search.domain.models.Track

sealed interface FavouritesScreenState {

    object Loading : FavouritesScreenState

    object Empty : FavouritesScreenState

    data class Content(val tracks: List<Track>) : FavouritesScreenState
}