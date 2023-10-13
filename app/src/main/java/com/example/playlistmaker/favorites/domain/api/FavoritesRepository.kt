package com.example.playlistmaker.favorites.domain.api

import com.example.playlistmaker.search.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    suspend fun addFavorite(track: Track)
    suspend fun deleteFavorite(track: Track)
    fun getFavorites(): Flow<List<Track>>

}