package com.example.playlistmaker.search.domain.api

import com.example.playlistmaker.search.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface SearchInteractor {
    fun searchTracks(query: String): Flow<Pair<List<Track>?, String?>>
    fun getSearchHistory(): Flow<List<Track>>
    fun addTrackToSearchHistory(track: Track)
    fun clearSearchHistory()
}