package com.example.playlistmaker.search.domain.api

import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.search.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface TrackRepository {
    fun search(expression: String): Flow<Resource<ArrayList<Track>>>
}