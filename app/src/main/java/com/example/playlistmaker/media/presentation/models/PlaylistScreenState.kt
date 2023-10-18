package com.example.playlistmaker.media.presentation.models

import com.example.playlistmaker.media.domain.models.Playlist

sealed interface PlaylistScreenState {
    object Empty : PlaylistScreenState

    data class NotEmpty(val content: Playlist) : PlaylistScreenState

    data class Filled(val content: Playlist) : PlaylistScreenState
}