package com.example.playlistmaker.media.domain.api

import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {

    suspend fun getFavouriteTracks(): Flow<List<Track>>

    suspend fun getFavouriteState(trackId: Long): Boolean

    suspend fun saveFavouriteTrack(track: Track)

    suspend fun deleteFavouriteTrack(trackId: Long)
}