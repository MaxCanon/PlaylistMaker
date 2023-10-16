package com.example.playlistmaker.search.data

import com.example.playlistmaker.search.domain.model.Track

interface LocalStorage {
    fun getSearchHistory(): ArrayList<Track>
    fun addTrackToSearchHistory(track: Track)
    fun clearSearchHistory()
}