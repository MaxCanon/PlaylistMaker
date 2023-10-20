package com.example.playlistmaker.media.presentation.models

import com.example.playlistmaker.media.domain.models.Playlist

sealed interface PlaylistDetailsScreenState {

    object Loading : PlaylistDetailsScreenState

    data class Error(val message: String) : PlaylistDetailsScreenState

    data class Content(val data: Playlist) : PlaylistDetailsScreenState
}