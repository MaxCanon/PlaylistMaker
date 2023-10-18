package com.example.playlistmaker.media.presentation.models

import com.example.playlistmaker.media.domain.models.Playlist

sealed interface PlaylistScreenResult {
    object Canceled : PlaylistScreenResult

    data class Created(val content: Playlist) : PlaylistScreenResult
}