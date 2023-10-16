package com.example.playlistmaker.favorites.view_model

import com.example.playlistmaker.search.domain.model.Track

sealed interface FavoritesState {
    object EmptyFavorites : FavoritesState
    class DisplayFavorites(val tracks: List<Track>) : FavoritesState
}